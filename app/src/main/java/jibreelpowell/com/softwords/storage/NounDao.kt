package jibreelpowell.com.softwords.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import jibreelpowell.com.softwords.generate.generator.Noun

@Dao
interface NounDao {
    @Query("SELECT * FROM nouns ORDER BY RANDOM() LIMIT :number")
    fun loadRandom(number: Int): Single<List<Noun>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(nouns: List<Noun>): Completable
}