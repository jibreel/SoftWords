package jibreelpowell.com.softwords.generate

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.kotlin.subscribeBy
import jibreelpowell.com.softwords.generate.generator.Generator
import jibreelpowell.com.softwords.generate.generator.Pattern
import jibreelpowell.com.softwords.generate.generator.Sentence
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.storage.SentenceDao
import jibreelpowell.com.softwords.storage.SentenceRepository
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class GenerateViewModel(
    private val sentenceRepository: SentenceRepository,
    private val generator: Generator,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    val isLoading = ObservableBoolean()

    val sentence: MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    val storageResult: MutableLiveData<Result<GeneratedSentence>> by lazy {
        MutableLiveData()
    }

    val generateResult: MutableLiveData<Result<GeneratedSentence>> by lazy {
        MutableLiveData()
    }

    fun generateNewSentence() {
        isLoading.set(true)
        Log.d("LOG_WITHOUT_PERMIT", "GenerateViewModel:generateNewSentence, generating sentence")
        viewModelScope.launch {
            val result = generator.generateRandomSentence(Pattern.random())
            if (result.isSuccess) {
                // Happy Path
                Log.d("LOG_WITHOUT_PERMIT", "GenerateViewModel:generateNewSentence, success")
                val generatedSentence = GeneratedSentence.newInstance(result.getOrThrow().toString())
                sentence.value = generatedSentence.sentence
                generateResult.value = Result.success(generatedSentence)
            } else {
                // Error Path
                Log.d("LOG_WITHOUT_PERMIT", "GenerateViewModel:generateNewSentence, failure")
                val e = result.exceptionOrNull()
                Timber.e(e)
                generateResult.value = Result.failure(e!!)
            }
        }
    }

    fun storeCurrentSentence() {
        sentence.value?.let {
            val generatedSentence = GeneratedSentence.newInstance(it)
            viewModelScope.launch {
                val result = sentenceRepository.insertSentence(generatedSentence)
                if (result.isSuccess) {
                    Timber.d("Sentence inserted into database successfully")
                    storageResult.value = result
                } else {
                    Timber.e(result.exceptionOrNull())
                    storageResult.value = result
                }
            }
        }
    }

}