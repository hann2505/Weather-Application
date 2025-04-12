package com.example.weatherapplication.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherData(
    val location: LocationData,
    val current: CurrentWeather,
    val forecast: Forecast
): Parcelable
