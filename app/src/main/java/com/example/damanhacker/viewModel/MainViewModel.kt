package com.example.damanhacker.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.damanhacker.api.apiInterface.Repository
import com.example.damanhacker.api.retrofit.RetrofitHelper
import com.example.damanhacker.database.DBHandler
import com.example.damanhacker.intefaces.onResponse
import com.example.damanhacker.model.DataModelMainData
import com.example.damanhacker.model.DataModelMainResponse
import com.example.damanhacker.model.RequestGetData
import com.example.damanhacker.utlities.Mapping
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(val context: Context, private val onResponse: onResponse) : ViewModel() {
    private var dbHandler = DBHandler(context)
    val apiCall: Repository = RetrofitHelper.getInstance().create(Repository::class.java)
    suspend fun getApi(requestData: RequestGetData): Response<DataModelMainResponse> {
        return apiCall.getData(requestData)
    }

    fun getData(date: String) {
        viewModelScope.launch {
            val requestData = RequestGetData(
                CHK = "GET_DAMAN_LIST", DATE = date
            )
            val response = getApi(requestData)
            if (response.isSuccessful) {
                response.body()?.values?.let {
                    onResponse.onSuccess(it)
                    prepareData(it).forEach { data ->
                        if (dbHandler.getCheck(data.date, data.period.toString()) == 0) {
                            dbHandler.InsertDataMaster(data)
                        }
                    }
                }

            } else {
                onResponse.Error("Failed")
            }
        }
    }

    private fun prepareData(data: ArrayList<DataModelMainData>): ArrayList<DataModelMainData> {
        val listData = ArrayList<DataModelMainData>()
        data.forEachIndexed { _, element ->
            listData.add(
                DataModelMainData(
                    sno = element.sno,
                    period = element.period,
                    number = element.number,
                    value = Mapping().getValue(element.number),
                    color = Mapping().getColor(element.number),
                    date = element.date,
                    flag = 0
                )
            )
        }
        return listData
    }
    private fun prepareDataPattern(data: ArrayList<DataModelMainData>): ArrayList<DataModelMainData> {
        val listData = ArrayList<DataModelMainData>()
        data.forEachIndexed { _, element ->
            listData.add(
                DataModelMainData(
                    sno = element.sno,
                    period = element.period,
                    number = element.number,
                    value = Mapping().getValue(element.number),
                    color = Mapping().getColor(element.number),
                    date = element.date,
                    flag = 0
                )
            )
        }
        return listData
    }

}
