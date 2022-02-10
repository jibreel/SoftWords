package jibreelpowell.com.softwords.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import jibreelpowell.com.softwords.generate.generator.Noun
import jibreelpowell.com.softwords.generate.generator.Preposition
import jibreelpowell.com.softwords.generate.generator.Verb
import jibreelpowell.com.softwords.utils.Converters
import jibreelpowell.com.softwords.utils.DATABASE_NAME
import jibreelpowell.com.softwords.utils.DispatcherProvider
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber

@Database(entities = [GeneratedSentence::class, Noun::class, Verb::class, Preposition::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sentencesDao(): SentenceDao
    abstract fun nounDao(): NounDao
    abstract fun verbDao(): VerbDao
    abstract fun prepositionDao(): PrepositionDao

    suspend fun checkInitialization(dispatcherProvider: DispatcherProvider) {
        try {
            withContext(dispatcherProvider.io) {
                val noun = nounDao().loadRandom(1)[0]
            }
            Timber.v("Database Initialized")
        } catch (e: Exception) {
            Timber.e(e, "Error initializing database")
        }
    }

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun createInstance(context: Context, dispatcherProvider: DispatcherProvider): AppDatabase =
            INSTANCE ?: synchronized(this)  {
                INSTANCE ?: buildDatabase(context, dispatcherProvider).also { INSTANCE = it }
            }

        fun getInstance(): AppDatabase = INSTANCE ?: throw DatabaseDoesNotExistException("Please initialize database with createInstance")

        private fun buildDatabase(context: Context, dispatcherProvider: DispatcherProvider): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val instance = createInstance(context, dispatcherProvider)
                        runBlocking(dispatcherProvider.io) {
                            try {
                                instance.nounDao().insertAll(initialNouns)
                                instance.verbDao().insertAll(initialVerbs)
                                instance.prepositionDao().insertAll(initialPrepositions)
                                Timber.v("Tables populated")
                            } catch (e: Exception) {
                                Timber.e(e,"Error initializing database")
                            }
                        }
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

    class DatabaseDoesNotExistException(message: String): Exception(message)
}