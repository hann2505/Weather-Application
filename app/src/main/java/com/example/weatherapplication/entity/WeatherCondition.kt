package com.example.weatherapplication.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherCondition(
    val text: String,
    val icon: String,
    val code: Int
): Parcelable
