package com.example.weatherapplication.entity

data class Weather(
    val icon: String,
    val temperature: Double,
    val windSpeed: Double,
    val humidity: Int,
    val chanceOfRain: Int
)
