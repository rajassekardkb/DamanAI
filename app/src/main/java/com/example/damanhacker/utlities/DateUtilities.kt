package com.example.damanhacker.utlities

import com.example.damanhacker.utlities.UtlString.Companion.DATE
import java.text.SimpleDateFormat
import java.util.Calendar

class DateUtilities {
    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val c: Calendar = Calendar.getInstance()
       // return sdf.format(c.time)
        return DATE
    }

    fun getTime(period: Int): String {
        var format = ""
        var hours = period / 60
        val minutes = period % 60
        if (period < 780) {
             format = "AM"
        } else {
             format = "PM"
        }

        if (hours > 12) {
            hours -= 12;
        } else if (hours == 0) {
            hours = 12;
        }
        return "$hours:$minutes:$format"
    }
}