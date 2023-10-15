package com.example.damanhacker.utlities

import com.example.damanhacker.model.DataModelMainData

class findMaxCount {

    fun main(list: ArrayList<DataModelMainData>, date: String) {
        println("\n")
        println("RAJASEKAR DATE-->" + date)
        val (maxNumber, maxCount) = findMaxRepeatedNumber(list)

        if (maxNumber != null) {
            println("The number with the maximum count is: $maxNumber (Count: $maxCount)")
        } else {
            println("The list is empty.")
        }
    }

    fun findMaxRepeatedNumber(dataList: List<DataModelMainData>): Pair<Int?, Int> {
        if (dataList.isEmpty()) {
            return Pair(null, 0)
        }

        val groupByCount = dataList.groupBy { it.number }
        var maxCount = 0
        var maxNumber: Int? = null

        for ((number, occurrences) in groupByCount) {
            if (occurrences.size > maxCount) {
                maxCount = occurrences.size
                maxNumber = number
            }
        }

        return Pair(maxNumber, maxCount)
    }
}