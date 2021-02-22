package jibreelpowell.com.softwords.generate

import androidx.databinding.BaseObservable
import jibreelpowell.com.softwords.generate.generator.Pattern
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.storage.SentencesDao
import javax.inject.Inject

class GenerateFragmentPresenter @Inject constructor(val sentencesDao: SentencesDao): BaseObservable() {

    lateinit var sentence: String

    init {
        generateNewSentence()
    }

    fun generateNewSentence() {
        sentence = Pattern.random().sentence()
        notifyChange()
    }

    fun storeCurrentSentence() {
        sentencesDao.insertAll(GeneratedSentence.newInstance(sentence))
    }

}