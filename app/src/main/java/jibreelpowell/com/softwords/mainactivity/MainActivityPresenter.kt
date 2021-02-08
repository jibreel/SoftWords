package jibreelpowell.com.softwords.mainactivity

import androidx.databinding.BaseObservable
import jibreelpowell.com.softwords.generate.generator.Pattern
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(): BaseObservable() {

    lateinit var sentence: String

    init {
        generateNewSentence()
    }

    fun generateNewSentence() {
        sentence = Pattern.random().sentence()
        notifyChange()
    }

}