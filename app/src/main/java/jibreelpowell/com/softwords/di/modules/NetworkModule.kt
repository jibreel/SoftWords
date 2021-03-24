package jibreelpowell.com.softwords.di.modules

import dagger.Module
import dagger.Provides
import jibreelpowell.com.softwords.BuildConfig
import jibreelpowell.com.softwords.network.linguarobot.LinguaRobotApiService
import jibreelpowell.com.softwords.network.utils.RapidApiHttpInterceptor
import jibreelpowell.com.softwords.network.words.WordsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClientForRapidApi(): OkHttpClient {
        val interceptor = RapidApiHttpInterceptor()
        val okBuilder =  OkHttpClient.Builder()
            .addInterceptor(interceptor)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            okBuilder.addInterceptor(loggingInterceptor)
        }
        return okBuilder.build()
    }

    @Singleton
    @Provides
    @Named("words")
    fun provideRetrofitForWordsApi(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.WORDS_API)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("linguaRobot")
    fun provideRetrofitforLinguaRobotApi(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.LINGUA_ROBOT_API)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideWordsApiService(@Named("words") retrofit: Retrofit): WordsApiService {
        return retrofit.create(WordsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideLinguaRobotApiService(@Named("linguaRobot") retrofit: Retrofit): LinguaRobotApiService {
        return retrofit.create(LinguaRobotApiService::class.java)
    }
}