package jibreelpowell.com.softwords.generate

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import jibreelpowell.com.softwords.generate.generator.Generator
import jibreelpowell.com.softwords.generate.generator.Pattern
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.storage.SentenceDao
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import timber.log.Timber
import javax.inject.Inject

class GenerateViewModel(
    private val sentenceDao: SentenceDao,
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
        generator.generateRandomSentence(Pattern.random())
            .observeOn(schedulerProvider.ui)
            .subscribeBy(
                onSuccess = {
                    Log.d("LOG_WITHOUT_PERMIT", "GenerateViewModel:generateNewSentence, success")
                    val generatedSentence = GeneratedSentence.newInstance(it.toString())
                    sentence.value = generatedSentence.sentence
                    generateResult.value = Result.success(generatedSentence)
                },
                onError = {
                    Log.d("LOG_WITHOUT_PERMIT", "GenerateViewModel:generateNewSentence, failure")
                    Timber.e(it)
                    generateResult.value = Result.failure(it)
                }
            )
    }

    fun storeCurrentSentence() {
        sentence.value?.let {
            val generatedSentence = GeneratedSentence.newInstance(it)
            sentenceDao.insertAll(generatedSentence)
                .scheduleCompletableInBackground(schedulerProvider)
                .subscribe(
                    {
                        Timber.d("Sentence inserted into database successfully")
                        storageResult.value = Result.success(generatedSentence)
                    },
                    { e ->
                        Timber.e(e)
                        storageResult.value = Result.failure(e)
                    }
                )
        }
    }

}