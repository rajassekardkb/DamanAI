package com.example.damanhacker.utlities

import android.os.Build
import com.example.damanhacker.database.DBHandler

import com.example.damanhacker.intefaces.onResultListCustoms


import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class CheckSerialNumberRelatedOptphp {
    private var matchingClear: Int = 0
    private var loopMax: Int = 0
    private var masterMatchValue: Int = 0
    private var serialNumberPosition: Int = 0
    private var previousPeriod: Int = 0
    private var currentPeriod: Int = 0
    private var serialNumberList: ArrayList<GetData> = ArrayList()
    private var dataList: ArrayList<DataModelMainData> = ArrayList()
    private var outPutResult: ArrayList<outPutResponse> = ArrayList()
    private var childList: ArrayList<ReportData> = ArrayList()
    private var onResultList_: onResultListCustoms? = null

    init {
        outPutResult = ArrayList()
        childList = ArrayList()
    }

    fun init(dbHandler: DBHandler, onResult: onResultListCustoms) {
        previousPeriod = 0
        childList.clear()
        val date = DateUtilities().getCurrentDate()
        val listData = dbHandler.getDataProcess_(date)
        val list_ = numberAttachedValue(listData)
            patternCheckBasedOnSerialNumber(list_, listData, onResult)
        outPutResult.add(outPutResponse(date, ArrayList(childList)))

        if (onResultList_ != null) {
            onResultList_!!.onItemText(outPutResult)
        }
    }

    fun patternCheckBasedOnSerialNumber(listN_: ArrayList<GetData>, _list: ArrayList<DataModelMainData>, onResult: onResultListCustoms) {
        dataList = _list
        serialNumberList = listN_
        onResultList_ = onResult
        picSerialNumberBasics()
    }

    fun picSerialNumberBasics() {
        println("NumberRelated size->" + serialNumberList.size)
        var serialNumberPositionMoveForward = 0

        while (serialNumberPositionMoveForward < serialNumberList.size) {
            serialNumberPosition = serialNumberList[serialNumberPositionMoveForward].position
            masterMatchValue = serialNumberList[serialNumberPositionMoveForward].value
            getMatch((serialNumberPosition + 1))
            serialNumberPositionMoveForward++
        }

        for (otp in outPutResult) {
            println("NumberRelated->date:" + otp.date)
            otp.list.forEach { data -> println("Enna Bro Eppdi Irukka--->"+data.toString() + "\n") }
        }
    }

    fun getMatch(startPosition: Int) {
        if (dataList.size == startPosition) {
            return
        }

        val value = StringBuilder()
        value.append("").append("P->").append(serialNumberPosition).append(":Value->").append(masterMatchValue)
            .append(":").append(DateUtilities().getTime(dataList[startPosition].period))

        val period = dataList[startPosition].period
        val number = masterMatchValue
        val matchValue = getValueSB(masterMatchValue)
        val time = DateUtilities().getTime(period)
        val level = matchingClear
        val gap = 0
        val data = ReportData(period, number, time, level, gap)
        loopMax = 0

        for (i in startPosition until dataList.size) {
            val currentValue = dataList[i].value

            if (valueMatching(matchValue, currentValue)) {
                loopMax++
                matchingClear++
                if (i == dataList.size - 1) {
                    addValue(data)
                }
            } else {
                addValue(data)
                value.setLength(0)
                break
            }
        }
    }

    fun addValue(data: ReportData) {
        val MIN_MATCHING_CLEAR = 6
        if (matchingClear >= MIN_MATCHING_CLEAR) {
            currentPeriod = data.period
            val pr = currentPeriod - previousPeriod
            previousPeriod = currentPeriod
            childList.add(ReportData(data.period, data.number, data.time, matchingClear, pr))
        }
        matchingClear = 0
    }

    private fun getValueSB(number: Int): String {
        var value = ""
        when (number) {
            0, 1, 2, 3, 9 -> value = "Small"
            4, 5, 6, 7, 8 -> value = "Big"
        }
        return value
    }

    fun valueMatching(matchValue: String, currentValue: String): Boolean {
        return !matchValue.equals(currentValue)
    }

    fun numberAttachedValue(arrayList: ArrayList<DataModelMainData>): ArrayList<GetData> {
        val repeatedValues = ArrayList<GetData>()
        var i = 0

        while (i < arrayList.size) {
            val currentValue = arrayList[i].number
            val period = arrayList[i].period
            val sequenceStartIndex = i

            while (i < arrayList.size && arrayList[i].number == currentValue) {
                i++
            }

            if (i - sequenceStartIndex > 1) {
                val data = GetData()
                data.value = currentValue
                data.position = sequenceStartIndex + 1
                data.period = period
                repeatedValues.add(data)
            }
        }
        return repeatedValues
    }


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


    data class GetData(
        var position: Int = 0,
        var value: Int = 0,
        var period: Int = 0
    )
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

}
