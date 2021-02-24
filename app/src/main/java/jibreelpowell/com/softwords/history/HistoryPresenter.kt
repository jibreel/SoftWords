package jibreelpowell.com.softwords.history

import androidx.databinding.ObservableField
import jibreelpowell.com.softwords.storage.SentencesDao
import jibreelpowell.com.softwords.utils.scheduleFlowableInBackground
import timber.log.Timber
import javax.inject.Inject

class HistoryPresenter @Inject constructor(sentencesDao: SentencesDao) {

    init {
        sentencesDao.loadAllSentences()
            .scheduleFlowableInBackground()
            .subscribe { sentences ->
                Timber.d("${sentences.size}")
                val allSentences = sentences.toList()
                historyText.set(allSentences.fold("")  { acc, new -> acc + new.sentence})
            }

    }

    var historyText = ObservableField<String>()

}