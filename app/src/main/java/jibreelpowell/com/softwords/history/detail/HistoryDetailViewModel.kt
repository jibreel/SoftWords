package jibreelpowell.com.softwords.history.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.storage.SentenceDao
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import jibreelpowell.com.softwords.utils.scheduleSingleInBackground
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import javax.inject.Inject

class HistoryDetailViewModel @Inject constructor(
    private val sentenceDao: SentenceDao,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    val sentenceText = MutableLiveData<String>()
    val sentenceTimestamp = MutableLiveData<String>()

    private val formatter = DateTimeFormatter.ofPattern("MMMM d, YYYY h:mm a")

    val loadResult: MutableLiveData<Result<GeneratedSentence>> by lazy {
        MutableLiveData()
    }

    val deleteResult: MutableLiveData<Result<GeneratedSentence>> by lazy {
        MutableLiveData()
    }

    val shareResult: MutableLiveData<Result<Boolean>> by lazy {
        MutableLiveData()
    }

    private var shareCallback: (sentence: GeneratedSentence) -> Unit = {
        Timber.v("Share Callback never set")
    }

    val isLoading = MutableLiveData<Boolean>()

    fun load(id: Long) {
        isLoading.value = true
        sentenceDao.loadSentence(id)
            .scheduleSingleInBackground(schedulerProvider)
            .subscribeBy(
                onSuccess = {
                    loadResult.postValue(Result.success(it))
                },
                onError = { e ->
                    Timber.e(e)
                    loadResult.postValue(Result.failure(e))
                }
            )
    }

    fun updateUiForSentence(generatedSentence: GeneratedSentence) {
        isLoading.postValue(false)
        sentenceText.postValue(generatedSentence.sentence)
        sentenceTimestamp.postValue(generatedSentence.timestamp.format(formatter))
    }

    fun delete() {
        isLoading.postValue(true)
        loadResult.value?.getOrNull()?.let {
            sentenceDao.delete(it).scheduleCompletableInBackground(schedulerProvider).subscribeBy(
                onComplete = {
                    deleteResult.postValue(Result.success(it))
                },
                onError = { e ->
                    isLoading.postValue(false)
                    Timber.e(e)
                    deleteResult.postValue(Result.failure(e))
                }
            )
        }
    }

    fun setShareCallback(callback: (GeneratedSentence) -> Unit) {
        shareCallback = callback
    }

    fun share() {
        val sentence = loadResult.value?.getOrNull()

        if (sentence != null) {
            shareCallback(sentence)
            shareResult.postValue(Result.success(true))
        } else {
            shareResult.postValue(Result.failure(IllegalStateException("No Generated Sentence to be shared")))
        }
    }
}