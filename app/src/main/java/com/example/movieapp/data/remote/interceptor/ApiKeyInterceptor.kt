package com.example.movieapp.data.remote.interceptor

import com.example.movieapp.util.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .url(
                chain.request().url.newBuilder()
                    .addQueryParameter("api_key", NetworkUtils.API_KEY)
                    .build()
            )
            .build()

        return chain.proceed(request)
    }
}