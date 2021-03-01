package jibreelpowell.com.softwords.history

import jibreelpowell.com.softwords.storage.SentencesDao
import jibreelpowell.com.softwords.utils.scheduleFlowableInBackground
import javax.inject.Inject

class HistoryPresenter @Inject constructor(
    sentencesDao: SentencesDao,
    val historyAdapter: HistoryAdapter
) {

    init {
        sentencesDao.loadAllSentences()
            .scheduleFlowableInBackground()
            .subscribe { sentences ->
                historyAdapter.submitList(sentences.toList())
            }

    }
}