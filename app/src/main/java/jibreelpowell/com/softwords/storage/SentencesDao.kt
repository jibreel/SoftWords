package jibreelpowell.com.softwords.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface SentencesDao {
    @Query("SELECT * FROM sentences")
    fun loadAllSentences(): Flowable<Array<GeneratedSentence>>

    @Insert
    fun insertAll(vararg sentences: GeneratedSentence): Completable

    @Delete
    fun delete(sentence: GeneratedSentence): Completable
}