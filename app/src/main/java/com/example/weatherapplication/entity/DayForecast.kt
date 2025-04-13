package com.example.weatherapplication.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayForecast(
    val date: String,
    val day: Day,
    val hour: List<HourForecast>
): Parcelable
