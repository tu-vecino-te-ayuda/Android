package org.tuvecinoteayuda.data

import okhttp3.Interceptor
import okhttp3.Response

class CommonInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            //.addHeader("Authorization", "Bearer " + "TOKEN")
            .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Origin", "https://tuvecinoteayuda.org")
            .build()

        return chain.proceed(request)
    }

    companion object {

        fun newInstance(): CommonInterceptor {
            return CommonInterceptor()
        }
    }
}

