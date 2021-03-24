package jibreelpowell.com.softwords.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import jibreelpowell.com.softwords.generate.generator.Preposition

@Dao
interface PrepositionDao {
    @Query("SELECT * FROM prepositions ORDER BY RANDOM() LIMIT :number")
    fun loadRandom(number: Int): Single<List<Preposition>>

    @Insert
    fun insertAll(prepositions: List<Preposition>): Completable
}