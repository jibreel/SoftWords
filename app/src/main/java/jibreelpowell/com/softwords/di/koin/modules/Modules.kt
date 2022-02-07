package jibreelpowell.com.softwords.di.koin.modules

import android.content.Context
import jibreelpowell.com.softwords.BuildConfig
import jibreelpowell.com.softwords.generate.GenerateViewModel
import jibreelpowell.com.softwords.generate.generator.Word
import jibreelpowell.com.softwords.history.HistoryAdapter
import jibreelpowell.com.softwords.history.HistoryViewModel
import jibreelpowell.com.softwords.history.item.HistoryItemPresenter
import jibreelpowell.com.softwords.network.linguarobot.LinguaRobotApiService
import jibreelpowell.com.softwords.network.utils.RapidApiHttpInterceptor
import jibreelpowell.com.softwords.network.words.WordsApiService
import jibreelpowell.com.softwords.settings.SettingsViewModel
import jibreelpowell.com.softwords.storage.*
import jibreelpowell.com.softwords.utils.SchedulerProvider
import jibreelpowell.com.softwords.utils.SchedulerProviderImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.compose.get
import org.koin.core.qualifier.named
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Modules {

    val utilityModule = module {
        single<SchedulerProvider> { SchedulerProviderImpl() }
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
        single<AppDatabase> { params ->
            val schedulerProvider: SchedulerProvider = get()
            val context: Context = params.get()
            AppDatabase.getInstance(context, schedulerProvider)
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
    }

    val historyModule = module {
        factory<HistoryItemPresenter> { HistoryItemPresenter() }

        factory<HistoryAdapter> { HistoryAdapter() }

    }

    val viewModelModule = module {
        single<SettingsViewModel> { SettingsViewModel(get(), get()) }

        single<HistoryViewModel> { HistoryViewModel(get(), get()) }

        single<GenerateViewModel> { GenerateViewModel(get(), get(), get()) }
    }
}