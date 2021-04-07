package jibreelpowell.com.softwords.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface SentenceDao {
    @Query("SELECT * FROM sentences")
    fun loadAllSentences(): LiveData<List<GeneratedSentence>>

    @Query("SELECT * FROM sentences WHERE id = :id")
    fun loadSentence(id: Long): Single<GeneratedSentence>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg sentences: GeneratedSentence): Completable

    @Delete
    fun delete(sentence: GeneratedSentence): Completable
}