package jibreelpowell.com.softwords.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import io.reactivex.rxjava3.kotlin.subscribeBy
import jibreelpowell.com.softwords.generate.generator.Noun
import jibreelpowell.com.softwords.generate.generator.Preposition
import jibreelpowell.com.softwords.generate.generator.Verb
import jibreelpowell.com.softwords.utils.Converters
import jibreelpowell.com.softwords.utils.DATABASE_NAME
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.scheduleSingleInBackground
import timber.log.Timber

@Database(entities = [GeneratedSentence::class, Noun::class, Verb::class, Preposition::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sentencesDao(): SentenceDao
    abstract fun nounDao(): NounDao
    abstract fun verbDao(): VerbDao
    abstract fun prepositionDao(): PrepositionDao

    fun checkInitialization(schedulerProvider: SchedulerProvider) {
        nounDao().loadRandom(1)
            .scheduleSingleInBackground(schedulerProvider)
            .subscribeBy(
                onSuccess = { Timber.v("Database Initialized")},
                onError = { Timber.e(it) }
            )
    }

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, schedulerProvider: SchedulerProvider): AppDatabase =
            INSTANCE ?: synchronized(this)  {
                INSTANCE ?: buildDatabase(context, schedulerProvider).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context, schedulerProvider: SchedulerProvider): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val instance = getInstance(context, schedulerProvider)
                        val completable1 =
                            instance.nounDao().insertAll(initialNouns).subscribeOn(schedulerProvider.io)
                        val completable2 =
                            instance.verbDao().insertAll(initialVerbs).subscribeOn(schedulerProvider.io)
                        val completable3 = instance.prepositionDao().insertAll(initialPrepositions)
                            .subscribeOn(schedulerProvider.io)

                        completable1.mergeWith(completable2).mergeWith(completable3)
                            .observeOn(schedulerProvider.ui)
                            .subscribeBy(
                                onComplete = { Timber.v("Table Populated") },
                                onError = { t -> Timber.e(t) }
                            )

                    }
                }
            ).build()
        }

        private val initialNouns = listOf(
            Noun("leaf", "leaves"),
            Noun("water"),
            Noun("rock"),
            Noun("card"),
            Noun("fish", "fish"),
            Noun("person", "people"),
            Noun("bug"),
            Noun("galaxy", "galaxies"),
            Noun("napkin"),
            Noun("word")
        )

        private val initialVerbs =  listOf(
            Verb("fall"),
            Verb("swim"),
            Verb("am", "are", "are", "is", "are"),
            Verb("have", "have", "have", "has", "have"),
            Verb("run"),
            Verb("meander"),
            Verb("beget"),
            Verb("fortell"),
            Verb("live"),
            Verb("listen"),
            Verb("write")
        )

        private val initialPrepositions =  listOf(
            Preposition("in"),
            Preposition("on"),
            Preposition("from"),
            Preposition("below"),
            Preposition("under"),
            Preposition("inside of"),
            Preposition("between"),
            Preposition("like"),
            Preposition("because of"),
            Preposition("except")
        )
    }
}