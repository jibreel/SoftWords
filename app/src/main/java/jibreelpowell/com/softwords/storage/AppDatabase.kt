package jibreelpowell.com.softwords.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GeneratedSentence::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sentencesDao(): SentencesDao
}