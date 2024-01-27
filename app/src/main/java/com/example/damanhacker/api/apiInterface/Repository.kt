package com.example.damanhacker.api.apiInterface

import com.example.damanhacker.api.response.testResponse
import com.example.damanhacker.model.DataModelMainResponse
import com.example.damanhacker.model.DateCount
import com.example.damanhacker.model.ListDateCount
import com.example.damanhacker.model.RequestGetData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Repository {
    @GET("API.php")
    suspend fun getQuotes(): Response<testResponse>

    @POST("API.php")
    suspend fun getData(@Body data: RequestGetData): Response<DataModelMainResponse>

    @POST("getDataList.php")
    suspend fun getDateList(@Body data: RequestGetData): Response<DateCount>
}