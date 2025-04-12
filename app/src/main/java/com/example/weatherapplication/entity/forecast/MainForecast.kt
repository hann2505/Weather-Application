package com.example.weatherapplication.entity.forecast

import com.example.weatherapplication.entity.WeatherCondition

data class MainForecast(
    val localtime: String,
    val temp: Double,
    val condition: WeatherCondition,
    val location: String,
    val maxtemp_c: Double,
    val mintemp_c: Double
)
