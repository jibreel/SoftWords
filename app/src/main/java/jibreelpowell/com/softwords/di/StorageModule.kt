package jibreelpowell.com.softwords.di

import android.content.Context
import dagger.Module
import dagger.Provides
import jibreelpowell.com.softwords.storage.AppDatabase
import jibreelpowell.com.softwords.storage.NounDao
import jibreelpowell.com.softwords.storage.PrepositionDao
import jibreelpowell.com.softwords.storage.SentenceDao
import jibreelpowell.com.softwords.storage.VerbDao
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideSentencesDao(appDatabase: AppDatabase): SentenceDao {
        return appDatabase.sentencesDao()
    }

    @Provides
    fun provideNounDao(appDatabase: AppDatabase): NounDao {
        return appDatabase.nounDao()
    }

    @Provides
    fun provideVerbDao(appDatabase: AppDatabase): VerbDao {
        return appDatabase.verbDao()
    }

    @Provides
    fun providePrepositionDao(appDatabase: AppDatabase): PrepositionDao {
        return appDatabase.prepositionDao()
    }

}