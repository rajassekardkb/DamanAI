package com.example.damanhacker.api.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    const val baseUrl = "https://trigonometric-disco.000webhostapp.com/app/"
    private val logging =  HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private val client =  OkHttpClient.Builder()
    .addInterceptor(logging)
    .build();
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}