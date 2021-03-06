package jibreelpowell.com.softwords.generate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jibreelpowell.com.softwords.generate.generator.Pattern
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.storage.SentenceDao
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import timber.log.Timber
import javax.inject.Inject

class GenerateViewModel @Inject constructor(private val sentenceDao: SentenceDao) :
    ViewModel() {

    val sentence: MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    val storageResult: MutableLiveData<Result<GeneratedSentence>> by lazy {
        MutableLiveData()
    }

    init {
        generateNewSentence()
    }

    fun generateNewSentence() {
        sentence.value = Pattern.random().sentence()
    }

    fun storeCurrentSentence() {
        sentence.value?.let {
            val generatedSentence = GeneratedSentence.newInstance(it)
            sentenceDao.insertAll(generatedSentence)
                .scheduleCompletableInBackground()
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