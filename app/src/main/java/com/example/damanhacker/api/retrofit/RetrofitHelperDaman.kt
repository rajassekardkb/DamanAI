package com.example.damanhacker.api.retrofit

import com.example.damanhacker.api.apiInterface.SupportInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelperDaman {

    const val baseUrl = "https://envyenvelope.com/api/webapi/"

    //I used setLogLevel(LogLevel.FULL).setLog(new AndroidLog("YOUR_LOG_TAG")), it helped me.
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging).connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        //.addInterceptor(SupportInterceptor())
        .build()

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}