package jibreelpowell.com.softwords.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable

@Dao
interface SentenceDao {
    @Query("SELECT * FROM sentences")
    fun loadAllSentences(): LiveData<List<GeneratedSentence>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(vararg sentences: GeneratedSentence)

    @Delete
    suspend fun delete(sentence: GeneratedSentence)
}