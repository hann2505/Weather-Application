package com.example.weatherapplication.extension

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object TimeConverter {
    private val currentTime = System.currentTimeMillis()

    private fun dateFormatter(): SimpleDateFormat {
        return SimpleDateFormat("MM-dd", java.util.Locale.getDefault())
    }

    private fun timeFormatter(): SimpleDateFormat {
        return SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
    }

    private fun getDayOfWeekFromTimestamp(): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE") // "EEEE" gives full day name (e.g., Monday)
        return Instant.ofEpochMilli(currentTime)
            .atZone(ZoneId.systemDefault()) // Convert to local time zone
            .format(formatter)
    }

    fun convertTimeToReadableData(): String {
        val date = dateFormatter().format(currentTime)
        val time = timeFormatter().format(currentTime)
        val dayOfWeek = getDayOfWeekFromTimestamp()

        return "$dayOfWeek $date | $time"
    }
}