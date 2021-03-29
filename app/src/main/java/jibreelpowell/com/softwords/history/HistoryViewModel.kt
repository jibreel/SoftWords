package jibreelpowell.com.softwords.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.storage.SentenceDao
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import timber.log.Timber
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    val sentenceDao: SentenceDao,
    val schedulerProvider: SchedulerProvider
) : ViewModel()  {

    val allSentences = sentenceDao.loadAllSentences()
    val deletionResult: MutableLiveData<Result<GeneratedSentence>> by lazy {
        MutableLiveData()
    }

    fun delete(index: Int) {
        val currentSentences = allSentences.value ?: arrayListOf()
        val deletedSentence = currentSentences.getOrNull(index)

        if (deletedSentence == null) {
            deletionResult.postValue(Result.failure(IllegalStateException("No sentence exists at specified index")))
            return
        }

        sentenceDao.delete(deletedSentence).scheduleCompletableInBackground(schedulerProvider).subscribeBy(
            onComplete = { deletionResult.postValue(Result.success(deletedSentence)) },
            onError = { e ->
                Timber.e(e)
                deletionResult.postValue(Result.failure(e))
            }
        )
    }
}