package org.tuvecinoteayuda.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.tuvecinoteayuda.data.BuildConfig.SERVER_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory {

    companion object {
        inline fun <reified S> create(interceptor: CommonInterceptor): S {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor(interceptor)

            val retrofit: Retrofit = Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_BASE_URL)
                .build()

            return retrofit.create(S::class.java)
        }
    }
}
