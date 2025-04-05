package com.example.weatherapplication.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(WeatherAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api: WeatherAPI by lazy {
            retrofit.create(WeatherAPI::class.java)
        }
    }
}