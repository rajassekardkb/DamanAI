package com.example.damanhacker.api.apiInterface

import com.example.damanhacker.api.response.testResponse
import retrofit2.Response
import retrofit2.http.GET

interface test {
    @GET("API.php")
    suspend fun getQuotes() : Response<testResponse>
}