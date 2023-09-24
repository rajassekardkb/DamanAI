package com.example.damanhacker.model

import com.google.gson.annotations.SerializedName

data class DataModelMainResponse(
    val status:Boolean,
    val values: ArrayList<DataModelMainData>?
)

data class DataModelMainData(
    @SerializedName("DM_SNO")
    val sno: Int,
    @SerializedName("DM_PERIOD")
    val period: Int,
    @SerializedName("DM_NUMBER")
    val number: Int,
    @SerializedName("DM_VALUE")
    val value: String,
    @SerializedName("DM_COLOR")
    val color: String,
    @SerializedName("DM_DATE")
    val date: String,
    @SerializedName("DM_FLAG")
    val flag: Int
)