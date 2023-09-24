package com.example.damanhacker.api.apiInterface

import com.example.damanhacker.api.response.testResponse
import com.example.damanhacker.model.DamanRequestGetData
import com.example.damanhacker.model.DataModelMainResponse
import com.example.damanhacker.model.RequestGetData
import com.example.damanhacker.model.damanServerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST

interface DamanServerRepository {
    @GET("API.php")
    suspend fun getQuotes(): Response<testResponse>

    @POST("GetNoaverageEmerdList")

  /*  @Headers(
        "Content-Type: application/json",
        "X-Access-Token:  5e3fcc78ef404a85ab3dd961ecfeed1f",
        "Accept-Language:  en-US,en;q0.5",
        "Accept-Encoding:  gzip, deflate, br",
         "Connection:  keep-alive",
       // "Accept:  ",
        "Host:  envyenvelope.com",
        "Content-Length:  154"
    )*/


    suspend fun getData( @Body data: DamanRequestGetData
    ): Response<damanServerResponse>
}