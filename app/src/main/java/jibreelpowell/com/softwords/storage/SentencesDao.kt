package jibreelpowell.com.softwords.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SentencesDao {
    @Query("SELECT * FROM sentences")
    fun loadAllSentences(): Array<GeneratedSentence>

    @Insert
    fun insertAll(vararg sentences: GeneratedSentence)

    @Delete
    fun delete(sentence: GeneratedSentence)
}