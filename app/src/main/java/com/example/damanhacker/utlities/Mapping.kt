package com.example.damanhacker.utlities

import com.example.damanhacker.model.DataModelMainData
import java.util.*

class Mapping {

    fun getDummyData(): ArrayList<DataModelMainData> {
        val data = ArrayList<DataModelMainData>()
        for (i in 0..100) {
            val randomNumber: Int = Random().nextInt(9) + 1
            val dataModel = DataModelMainData(i, i, randomNumber, "", getColor(randomNumber), "", 0)
            data.add(dataModel)

        }

        for (i in data.indices) {
            //     println("$i:${data[i].color}")
        }
        return data
    }

    fun getColor(number: Int): String {
        var color = ""
        when (number) {
            1, 3, 7, 9 -> {
                color = "G"
            }

            2, 4, 6, 8 -> {
                color = "R"
            }

            0 -> {
                color = "RV"
            }

            5 -> {
                color = "GV"
            }

        }
        return color
    }

    fun getValue(number: Int): String {
        var value = ""
        when (number) {
            0, 1, 2, 3, 4 -> {
                value = "Small"
            }

            5, 6, 7, 8, 9 -> {
                value = "Big"
            }

        }
        return value
    }
}