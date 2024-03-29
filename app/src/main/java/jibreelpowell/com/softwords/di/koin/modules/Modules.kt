package jibreelpowell.com.softwords.di.koin.modules

import android.content.Context
import jibreelpowell.com.softwords.BuildConfig
import jibreelpowell.com.softwords.generate.GenerateViewModel
import jibreelpowell.com.softwords.generate.generator.Generator
import jibreelpowell.com.softwords.history.HistoryAdapter
import jibreelpowell.com.softwords.history.HistoryViewModel
import jibreelpowell.com.softwords.history.item.HistoryItemPresenter
import jibreelpowell.com.softwords.network.linguarobot.LinguaRobotApiService
import jibreelpowell.com.softwords.network.utils.RapidApiHttpInterceptor
import jibreelpowell.com.softwords.network.words.WordsApiService
import jibreelpowell.com.softwords.settings.SettingsViewModel
import jibreelpowell.com.softwords.storage.AppDatabase
import jibreelpowell.com.softwords.storage.NounDao
import jibreelpowell.com.softwords.storage.PrepositionDao
import jibreelpowell.com.softwords.storage.SentenceDao
import jibreelpowell.com.softwords.storage.SentenceRepository
import jibreelpowell.com.softwords.storage.VerbDao
import jibreelpowell.com.softwords.storage.WordRepository
import jibreelpowell.com.softwords.utils.DispatcherProvider
import jibreelpowell.com.softwords.utils.DispatcherProviderImpl
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.SchedulerProviderImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Modules {

    val utilityModule = module {
        single<SchedulerProvider> { SchedulerProviderImpl() }
        single<DispatcherProvider> { DispatcherProviderImpl() }
    }

    val networkModule = module {
        single<OkHttpClient> {
            val interceptor = RapidApiHttpInterceptor()
            val okBuilder =  OkHttpClient.Builder()
                .addInterceptor(interceptor)
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
                okBuilder.addInterceptor(loggingInterceptor)
            }
            okBuilder.build()
        }

        single<Retrofit>(named("words")) {
            val okHttpClient: OkHttpClient = get()
            Retrofit.Builder()
                .baseUrl(BuildConfig.WORDS_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        }

        single<Retrofit>(named("linguaRobot")) {
            val okHttpClient: OkHttpClient = get()
            Retrofit.Builder()
                .baseUrl(BuildConfig.LINGUA_ROBOT_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        }

        single<WordsApiService> {
            val retrofit: Retrofit by inject(qualifier = named("words"))
            retrofit.create(WordsApiService::class.java)
        }

        single<LinguaRobotApiService> {
            val retrofit: Retrofit by inject(qualifier = named("linguaRobot"))
            retrofit.create(LinguaRobotApiService::class.java)
        }
    }

    val storageModule = module {
        factory<AppDatabase> {
            AppDatabase.getInstance()
        }

        single<SentenceDao> {
            val appDatabase: AppDatabase = get()
            appDatabase.sentencesDao()
        }

        single<NounDao> {
            val appDatabase: AppDatabase = get()
            appDatabase.nounDao()
        }

        single<VerbDao> {
            val appDatabase: AppDatabase = get()
            appDatabase.verbDao()
        }

        single<PrepositionDao> {
            val appDatabase: AppDatabase = get()
            appDatabase.prepositionDao()
        }

        single<WordRepository> { WordRepository() }

        single<SentenceRepository> { SentenceRepository() }

        single<Generator> { Generator() }
    }

    val historyModule = module {
        factory<HistoryItemPresenter> { HistoryItemPresenter() }

        factory<HistoryAdapter> { HistoryAdapter() }

    }

    val viewModelModule = module {
        single<SettingsViewModel> { SettingsViewModel(get()) }

        single<HistoryViewModel> { HistoryViewModel(get(), get()) }

        single<GenerateViewModel> { GenerateViewModel(get(), get(), get()) }
    }
}