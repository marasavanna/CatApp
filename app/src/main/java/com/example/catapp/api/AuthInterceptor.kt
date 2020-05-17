package com.example.catapp.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
        val url = req.url().newBuilder()
            .addQueryParameter("APPID", "a7ad3e80-678c-4d94-a041-76d5fea650a0").build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}