package com.example.weatherapplication.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HourForecast(
    val time: String,
    @SerializedName("temp_c")
    val temperature: Double,
    @SerializedName("feelslike_c")
    val feelsLikeTemperature: Double,
    val condition: WeatherCondition
): Parcelable
