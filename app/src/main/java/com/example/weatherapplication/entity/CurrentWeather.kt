package com.example.weatherapplication.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentWeather(
    @SerializedName("temp_c")
    val temperature: Double,
    val condition: WeatherCondition,
    @SerializedName("wind_kph")
    val windSpeed: Double,
    @SerializedName("humidity")
    val humidity: Int
): Parcelable
