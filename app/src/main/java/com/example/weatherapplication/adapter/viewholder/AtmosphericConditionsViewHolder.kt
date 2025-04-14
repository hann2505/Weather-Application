package com.example.weatherapplication.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.AtmosphericConditionWeatherBinding
import com.example.weatherapplication.entity.CurrentWeather
import com.example.weatherapplication.entity.WeatherData

class AtmosphericConditionsViewHolder(private val binding: AtmosphericConditionWeatherBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(weatherData: WeatherData) {
        binding.chanceOfRain.text = binding.root.context.getString(R.string.ratio, weatherData.forecast.forecastDay[0].day.chanceOfRain)
        binding.humidityRate.text = binding.root.context.getString(R.string.ratio, weatherData.current.humidity)
        binding.windSpeed.text = binding.root.context.getString(R.string.speed, weatherData.current.windSpeed)
    }
}