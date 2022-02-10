package jibreelpowell.com.softwords.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.kotlin.subscribeBy
import jibreelpowell.com.softwords.generate.generator.Word
import jibreelpowell.com.softwords.storage.WordRepository
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.scheduleCompletableInBackground
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SettingsViewModel(
    private val wordRepository: WordRepository
) : ViewModel() {

    val storageResult: MutableLiveData<Result<Word.PartOfSpeech>> by lazy {
        MutableLiveData()
    }

    var lastRequestedAddition: Word.PartOfSpeech? = null

    fun addNewNoun() {
        lastRequestedAddition = Word.PartOfSpeech.NOUN
        viewModelScope.launch {
            val result = wordRepository.addNewNounToStorage()
            if (result.isSuccess) {
                storageResult.postValue(Result.success(Word.PartOfSpeech.NOUN))
            } else {
                Timber.e(result.exceptionOrNull())
                storageResult.postValue(Result.failure(result.exceptionOrNull()!!))
            }

        }
    }

    fun addNewVerb() {
        lastRequestedAddition = Word.PartOfSpeech.VERB
        viewModelScope.launch {
            val result = wordRepository.addNewVerbToStorage()
            if (result.isSuccess) {
                storageResult.postValue(Result.success(Word.PartOfSpeech.VERB))
            } else {
                Timber.e(result.exceptionOrNull())
                storageResult.postValue(Result.failure(result.exceptionOrNull()!!))
            }
        }
    }

    fun addNewPreposition() {
        lastRequestedAddition = Word.PartOfSpeech.PREPOSITION
        viewModelScope.launch {
            val result = wordRepository.addNewPrepositionToStorage()
            if (result.isSuccess) {
                storageResult.postValue(Result.success(Word.PartOfSpeech.PREPOSITION))
            } else {
                Timber.e(result.exceptionOrNull())
                storageResult.postValue(Result.failure(result.exceptionOrNull()!!))
            }
        }
    }
}