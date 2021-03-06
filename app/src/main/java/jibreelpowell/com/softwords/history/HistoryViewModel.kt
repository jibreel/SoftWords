package jibreelpowell.com.softwords.history

import androidx.lifecycle.ViewModel
import jibreelpowell.com.softwords.storage.SentenceDao
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    sentenceDao: SentenceDao
) : ViewModel()  {

    val allSentences = sentenceDao.loadAllSentences()
}