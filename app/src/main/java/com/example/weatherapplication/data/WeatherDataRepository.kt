package com.example.weatherapplication.data

import com.example.weatherapplication.api.RetrofitInstance
import com.example.weatherapplication.entity.WeatherData

class WeatherDataRepository {

    suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherData {
        val response = RetrofitInstance.api.getWeatherData(location = "$latitude,$longitude")
        return response.body() ?: throw Exception("Failed to fetch weather data")
    }
}