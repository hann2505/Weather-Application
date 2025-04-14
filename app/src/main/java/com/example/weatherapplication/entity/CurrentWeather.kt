package com.example.weatherapplication.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//TODO isDay always equals to 0
@Parcelize
data class CurrentWeather(
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("temp_c")
    val temperature: Double,
    val condition: WeatherCondition,
    @SerializedName("wind_kph")
    val windSpeed: Double,
    @SerializedName("humidity")
    val humidity: Int
): Parcelable
