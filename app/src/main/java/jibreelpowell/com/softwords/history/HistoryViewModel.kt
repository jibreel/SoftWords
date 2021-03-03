package jibreelpowell.com.softwords.history

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import jibreelpowell.com.softwords.storage.SentencesDao
import jibreelpowell.com.softwords.utils.scheduleFlowableInBackground
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    sentencesDao: SentencesDao,
    val historyAdapter: HistoryAdapter
) : ViewModel()  {

    init {
        sentencesDao.loadAllSentences()
            .scheduleFlowableInBackground()
            .subscribe { sentences ->
                historyAdapter.submitList(sentences.toList())
            }

    }
}