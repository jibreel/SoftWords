package jibreelpowell.com.softwords.network.utils

import jibreelpowell.com.softwords.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RapidApiHttpInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(
                "x-rapidapi-key",
                BuildConfig.RAPID_API_KEY
            )
            .build()
        return chain.proceed(newRequest)
    }
}