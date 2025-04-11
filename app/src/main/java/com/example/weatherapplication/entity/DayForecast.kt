package com.example.weatherapplication.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayForecast(
    val day: Day,
    val hour: List<HourForecast>
): Parcelable
