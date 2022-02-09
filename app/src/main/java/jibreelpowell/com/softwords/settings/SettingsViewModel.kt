package jibreelpowell.com.softwords.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import jibreelpowell.com.softwords.generate.generator.Word
import jibreelpowell.com.softwords.storage.WordRepository
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import timber.log.Timber
import javax.inject.Inject

class SettingsViewModel(
    private val wordRepository: WordRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    val storageResult: MutableLiveData<Result<Word.PartOfSpeech>> by lazy {
        MutableLiveData()
    }

    var lastRequestedAddition: Word.PartOfSpeech? = null

    fun addNewNoun() {
        lastRequestedAddition = Word.PartOfSpeech.NOUN
        wordRepository.addNewNounToStorage()
            .scheduleCompletableInBackground(schedulerProvider)
            .subscribeBy(
                onComplete = {
                    storageResult.postValue(Result.success(Word.PartOfSpeech.NOUN))
                },
                onError = {
                    Timber.e(it)
                    storageResult.postValue(Result.failure(it))
                }
            )
    }

    fun addNewVerb() {
        lastRequestedAddition = Word.PartOfSpeech.VERB
        wordRepository.addNewVerbToStorage()
            .scheduleCompletableInBackground(schedulerProvider)
            .subscribeBy(
                onComplete = {
                    storageResult.postValue(Result.success(Word.PartOfSpeech.VERB))
                },
                onError = {
                    Timber.e(it)
                    storageResult.postValue(Result.failure(it))
                }
            )
    }

    fun addNewPreposition() {
        lastRequestedAddition = Word.PartOfSpeech.PREPOSITION
        wordRepository.addNewPrepositionToStorage()
            .scheduleCompletableInBackground(schedulerProvider)
            .subscribeBy(
                onComplete = {
                    storageResult.postValue(Result.success(Word.PartOfSpeech.PREPOSITION))
                },
                onError = {
                    storageResult.postValue(Result.failure(it))
                }
            )
    }
}