package jibreelpowell.com.softwords.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp

    @Singleton
    @Provides
    fun provideRetrofitForDictionaryApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl()
    }
}