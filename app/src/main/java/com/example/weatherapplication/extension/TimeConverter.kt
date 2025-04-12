package com.example.weatherapplication.extension

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object TimeConverter {
    private val currentTime = System.currentTimeMillis()

    private fun dateFormatter(): SimpleDateFormat {
        return SimpleDateFormat("MM-dd", Locale.getDefault())
    }

    private fun localtimeFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    }


    private fun timeFormatter(): SimpleDateFormat {
        return SimpleDateFormat("hh:mm a", Locale.getDefault())
    }

    fun localDateTime(dateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeString, localtimeFormatter())
    }

    private fun getDayOfWeekFromTimestamp(): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE")
        return Instant.ofEpochMilli(currentTime)
            .atZone(ZoneId.systemDefault())
            .format(formatter)
    }



    fun convertTimeToReadableData(): String {
        val date = dateFormatter().format(currentTime)
        val time = timeFormatter().format(currentTime)
        val dayOfWeek = getDayOfWeekFromTimestamp()

        return "$dayOfWeek $date | $time"
    }
}