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
        val _hours: String
        val _minutes: String

        var hours = period / 60
        val minutes = period % 60
        val format: String = if (period < 780) {
            "AM"
        } else {
            "PM"
        }

        if (hours > 12) {
            hours -= 12
        } else if (hours == 0) {
            hours = 12
        }

        _hours = if (hours < 10) {
            "0$hours"
        } else {
            hours.toString()

        }
        _minutes = if (minutes < 10) {
            "0$minutes"
        } else {
            minutes.toString()
        }
        return "$_hours:$_minutes:$format"
    }
}