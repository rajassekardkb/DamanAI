package com.example.damanhacker.api.apiInterface

import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class SupportInterceptor : Interceptor {

    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        println("mamu-->"+request.body?.contentLength().toString())

        request = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept-Encoding", " gzip, deflate, br")
            .addHeader("Host", "envyenvelope.com")
            .addHeader("Content-Length", request.body?.contentLength().toString())
            .build()
        return chain.proceed(request)
    }
}