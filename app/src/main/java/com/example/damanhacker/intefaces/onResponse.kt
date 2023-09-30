package com.example.damanhacker.intefaces

import com.example.damanhacker.model.DataModelMainData

interface onResponse {
    fun onSuccess(list: ArrayList<DataModelMainData>)
    fun Error(data: String)
}