package jibreelpowell.com.softwords.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import jibreelpowell.com.softwords.generate.generator.Word
import jibreelpowell.com.softwords.storage.WordRepository
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {

    val storageResult: MutableLiveData<Result<Word.PartOfSpeech>> by lazy {
        MutableLiveData()
    }

    var lastRequestedAddition: Word.PartOfSpeech? = null

    fun addNewNoun() {
        lastRequestedAddition = Word.PartOfSpeech.NOUN
        wordRepository.addNewNounToStorage()
            .scheduleCompletableInBackground()
            .subscribeBy(
                onComplete = {
                    storageResult.value = Result.success(Word.PartOfSpeech.NOUN)
                },
                onError = {
                    storageResult.value = Result.failure(it)
                }
            )
    }

    fun addNewVerb() {
        lastRequestedAddition = Word.PartOfSpeech.VERB
        wordRepository.addNewVerbToStorage()
            .scheduleCompletableInBackground()
            .subscribeBy(
                onComplete = {
                    storageResult.value = Result.success(Word.PartOfSpeech.VERB)
                },
                onError = {
                    storageResult.value = Result.failure(it)
                }
            )
    }

    fun addNewPreposition() {
        lastRequestedAddition = Word.PartOfSpeech.PREPOSITION
        wordRepository.addNewPrepositionToStorage()
            .scheduleCompletableInBackground()
            .subscribeBy(
                onComplete = {
                    storageResult.value = Result.success(Word.PartOfSpeech.PREPOSITION)
                },
                onError = {
                    storageResult.value = Result.failure(it)
                }
            )
    }
}