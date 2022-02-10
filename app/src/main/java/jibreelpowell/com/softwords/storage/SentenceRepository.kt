package jibreelpowell.com.softwords.storage

import androidx.lifecycle.LiveData
import jibreelpowell.com.softwords.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SentenceRepository: KoinComponent {

    private val sentenceDao: SentenceDao by inject()
    private val dispatcherProvider: DispatcherProvider by inject()

    suspend fun loadAllSentences(): Result<LiveData<List<GeneratedSentence>>> {
        return try {
            val sentences = withContext(dispatcherProvider.io) {
                sentenceDao.loadAllSentences()
            }
            Result.success(sentences)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteSentence(sentence: GeneratedSentence): Result<GeneratedSentence> {
        return try {
            withContext(dispatcherProvider.io) {
                sentenceDao.delete(sentence)
            }
            Result.success(sentence)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun insertSentence(sentence: GeneratedSentence): Result<GeneratedSentence> {
        return try {
            withContext(dispatcherProvider.io) {
                sentenceDao.insertAll(sentence)
            }
            Result.success(sentence)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}