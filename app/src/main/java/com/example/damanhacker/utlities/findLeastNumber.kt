package com.example.damanhacker.utlities

import com.example.damanhacker.model.DataModelMainData

class findLeastNumber {
    fun main(customArrayList: ArrayList<DataModelMainData>) {
        val gapCountMap = mutableMapOf<Int, Int>()
        val leastGapMap = mutableMapOf<Pair<Int, Int>, Int>()

        val lastIndices = mutableMapOf<Int, Int>()

        for (i in 0 until customArrayList.size) {
            val currentNumber = customArrayList[i].number
            val lastIndex = lastIndices[currentNumber]

            if (lastIndex != null) {
                val gap =
                    if (i >= lastIndex) i - lastIndex else (customArrayList.size - lastIndex) + i
                gapCountMap[currentNumber] = (gapCountMap[currentNumber] ?: 0) + 1

                val leastGap =
                    leastGapMap.getOrPut(currentNumber to customArrayList[lastIndex].number) { gap }
                if (gap < leastGap) {
                    leastGapMap[currentNumber to customArrayList[lastIndex].number] = gap
                }
            }

            lastIndices[currentNumber] = i
        }

        // Print the results
        for (number in 0..9) {
            val count = gapCountMap[number] ?: 0
            val leastGap = leastGapMap[number to ((number + 9) % 10)] ?: 0
            println("Number $number appears with a gap of one: $count times")
            println("Least gap for $number to another number: $leastGap")
        }
    }

}