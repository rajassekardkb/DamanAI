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
import com.example.damanhacker.utlities.PatternCheck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(val context: Context, private val onResponse: onResponse) : ViewModel() {
    private var dbHandler = DBHandler(context)
    val apiCall: Repository = RetrofitHelper.getInstance().create(Repository::class.java)
    suspend fun getApi(requestData: RequestGetData): Response<DataModelMainResponse> {
        return apiCall.getData(requestData)
    }

    fun getData(date: String) {
        var status = false
        var list = ArrayList<DataModelMainData>()
        viewModelScope.launch(Dispatchers.Main) {
            val requestData = RequestGetData(
                CHK = "GET_DAMAN_LIST", DATE = date
            )
            val response = getApi(requestData)
            if (response.isSuccessful) {
                response.body()?.values?.let {
                    status = true
                    list = it
                    onResponse.onSuccess(list)
                    insertData(list)
                }

            } else {
                status = false
                onResponse.Error("Failed")
            }
        }
    }

    private fun insertData(list: ArrayList<DataModelMainData>) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHandler.insertDataIfNotExists(prepareData(list))
            PatternCheck().pickDataDuplicateNumberMax(dbHandler)
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

    fun getDataDownloading(dbHandler: DBHandler, requestList: ArrayList<RequestGetData>) {
        viewModelScope.launch(Dispatchers.IO) {
            val allData = ArrayList<DataModelMainData>()
            val deferredResponses = requestList.map { element ->
                async {
                    val response = getApi(element)
                    if (response.isSuccessful) {
                        response.body()?.values?.let {
                            allData.addAll(it)
                            println("DataDownloading-> Request onSuccess->" + element)

                        }
                    } else {
                        println("DataDownloading-> Request Error->" + element)
                        onResponse.Error("Failed")
                    }
                }
            }

            // Wait for all async calls to complete
            deferredResponses.awaitAll()
            // Insert data into the database if not already exists
            dbHandler.insertDataIfNotExists(prepareData(allData))
        }
    }

}
