package com.example.damanhacker.model

data class outPutResponse(
    val date: String,
    val list: ArrayList<ReportData>
)

data class ReportData(
    val period: Int,
    val number: Int,
    val time: String,
    val level: Int,
    val gap: Int
)