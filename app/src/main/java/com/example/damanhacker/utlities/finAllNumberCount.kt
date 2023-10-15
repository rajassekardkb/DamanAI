package com.example.damanhacker.utlities

import com.example.damanhacker.model.DataModelMainData

class finAllNumberCount {

    fun main(list: ArrayList<DataModelMainData>, date: String) {
        println("\n")
        println("RAJASEKAR DATE-->" + date)

        val counts = countOccurrences(list)

        val sortedCounts = counts.entries.sortedByDescending { it.value }

        for ((number, count) in sortedCounts) {
            println("$number is repeated $count times.")
        }
    }

    private fun countOccurrences(dataList: List<DataModelMainData>): Map<Int, Int> {
        return dataList.groupingBy { it.number }.eachCount()
    }
}