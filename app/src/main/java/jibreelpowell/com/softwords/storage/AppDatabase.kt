package jibreelpowell.com.softwords.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jibreelpowell.com.softwords.utils.Converters

@Database(entities = [GeneratedSentence::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sentencesDao(): SentencesDao
}