package jibreelpowell.com.softwords.generate

import androidx.databinding.BaseObservable
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import jibreelpowell.com.softwords.generate.generator.Pattern
import jibreelpowell.com.softwords.storage.GeneratedSentence
import jibreelpowell.com.softwords.storage.SentenceDao
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import timber.log.Timber
import javax.inject.Inject

class GenerateFragmentPresenter @Inject constructor(val sentenceDao: SentenceDao) :
    BaseObservable() {

    lateinit var sentence: String

    init {
        generateNewSentence()
    }

    fun generateNewSentence() {
        sentence = Pattern.random().sentence()
        notifyChange()
    }

    fun storeCurrentSentence() {
        sentenceDao.insertAll(GeneratedSentence.newInstance(sentence))
            .scheduleCompletableInBackground()
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    Timber.d("Insert Complete!")
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                }
            })
    }

}