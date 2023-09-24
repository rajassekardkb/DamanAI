package com.example.damanhacker.model

import com.google.gson.annotations.SerializedName

class damanServerResponse(


    @SerializedName("data") var data: Data? = Data(),
    @SerializedName("code") var code: Int? = null,
    @SerializedName("msg") var msg: String? = null,
    @SerializedName("msgCode") var msgCode: Int? = null
)


data class Data(

    @SerializedName("list") var list: ArrayList<List> = arrayListOf(),
    @SerializedName("pageNo") var pageNo: Int? = null,
    @SerializedName("totalPage") var totalPage: Int? = null,
    @SerializedName("totalCount") var totalCount: Int? = null

)


data class List(

    @SerializedName("issueNumber") var issueNumber: String? = null,
    @SerializedName("number") var number: String? = null,
    @SerializedName("colour") var colour: String? = null,
    @SerializedName("premium") var premium: String? = null

)