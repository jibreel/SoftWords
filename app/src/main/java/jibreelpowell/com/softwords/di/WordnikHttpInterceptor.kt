package jibreelpowell.com.softwords.di

import okhttp3.Interceptor
import okhttp3.Response

class WordnikHttpInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .newBuilder()
            .addHeader(

            )
    }
}