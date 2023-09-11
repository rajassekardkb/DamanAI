package com.example.damanhacker.utlities

import com.example.damanhacker.model.DataModelMain
import java.util.*

class Mapping {

    fun getDummyData(): ArrayList<DataModelMain> {
        val data = ArrayList<DataModelMain>()
        for (i in 0..100) {
            val randomNumber: Int = Random().nextInt(9) + 1
            val color = if (randomNumber % 2 == 0) "G" else "R"
            //print("$i $color,")
           // print(color)
            val dataModel = DataModelMain(i, randomNumber, color)
            data.add(dataModel)

        }

        for (i in data.indices) {
            //     println("$i:${data[i].color}")
        }
        return data
    }
}