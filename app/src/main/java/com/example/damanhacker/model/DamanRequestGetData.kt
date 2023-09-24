package com.example.damanhacker.model

data class DamanRequestGetData(
    val pageSize: String,
    val pageNo: String,
    val typeId: String,
    val language: String,
    val random: String,
    val signature: String,
    val timestamp: String
)