package jibreelpowell.com.softwords.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import jibreelpowell.com.softwords.generate.generator.Verb

@Dao
interface VerbDao {
    @Query("SELECT * FROM verbs ORDER BY RANDOM() LIMIT :number")
    suspend fun loadRandom(number: Int): List<Verb>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(verb: List<Verb>)
}