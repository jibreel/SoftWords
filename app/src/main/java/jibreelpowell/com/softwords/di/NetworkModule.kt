package jibreelpowell.com.softwords.di

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpForWordnikApi() {

        val interceptor = Interceptor()
    }

    @Singleton
    @Provides
    fun provideRetrofitForWordnikApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl()
    }
}