package jibreelpowell.com.softwords.history.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jibreelpowell.com.softwords.storage.SentenceDao
import jibreelpowell.com.softwords.utils.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

class HistoryDetailViewModel @Inject constructor(
    private val sentenceDao: SentenceDao,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    val sentenceText = MutableLiveData<String>()
    val sentenceTimestamp = MutableLiveData<String>()

        var closeCallBack : () -> Unit = {
            Timber.v("Close Callback was never set")
        }

    fun load() {
        TODO("Load Sentence from DAO")
    }

    fun delete() {
        TODO("Delete Sentence from DAO")
    }

    fun close() {
        closeCallBack()
    }

    fun share() {
        TODO("Open Share Intent with Sentence")
    }
}