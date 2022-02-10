package jibreelpowell.com.softwords.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.kotlin.subscribeBy
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.storage.SentenceDao
import jibreelpowell.com.softwords.storage.SentenceRepository
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HistoryViewModel(
    val sentenceRepository: SentenceRepository,
    val schedulerProvider: SchedulerProvider
) : ViewModel()  {

    var allSentences: LiveData<List<GeneratedSentence>> = MutableLiveData()
    val deletionResult: MutableLiveData<Result<GeneratedSentence>> by lazy {
        MutableLiveData()
    }

    fun initialize() {
        viewModelScope.launch {
            val result = sentenceRepository.loadAllSentences()
            if (result.isSuccess) {
                allSentences = result.getOrThrow()
            } else {
                Timber.e(result.exceptionOrNull())
            }
        }
    }

    fun delete(index: Int) {
        val currentSentences = allSentences.value ?: arrayListOf()
        val deletedSentence = currentSentences.getOrNull(index)

        if (deletedSentence == null) {
            deletionResult.postValue(Result.failure(IllegalStateException("No sentence exists at specified index")))
            return
        }

        viewModelScope.launch {
            val result = sentenceRepository.deleteSentence(deletedSentence)
            if (result.isSuccess) {
                deletionResult.postValue(result)
            } else {
                Timber.e(result.exceptionOrNull())
                deletionResult.postValue(Result.failure(result.exceptionOrNull()!!))
            }
        }
    }
}