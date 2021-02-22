package jibreelpowell.com.softwords.history

import jibreelpowell.com.softwords.storage.SentencesDao
import javax.inject.Inject

class HistoryPresenter @Inject constructor(private val sentencesDao: SentencesDao) {

    val historyText: String
        get() {
            val allSentences = arrayListOf(sentencesDao.loadAllSentences())
            return allSentences.fold("") { acc, new -> acc + new}
        }

}