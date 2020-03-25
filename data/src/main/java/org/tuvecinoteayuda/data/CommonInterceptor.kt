package org.tuvecinoteayuda.data

import okhttp3.Interceptor
import okhttp3.Response

class CommonInterceptor(var tokenProvider: TokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()

        tokenProvider.token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        val build = request.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Origin", "https://tuvecinoteayuda.org")
            .build()

        return chain.proceed(build)
    }

    companion object {

        fun newInstance(tokenProvider: TokenProvider = TokenProvider): CommonInterceptor {
            return CommonInterceptor(tokenProvider)
        }
    }
}

