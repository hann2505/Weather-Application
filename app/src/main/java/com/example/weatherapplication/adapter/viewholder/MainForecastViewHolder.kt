package com.example.weatherapplication.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.MainForecastItemBinding
import com.example.weatherapplication.entity.forecast.MainForecast

class MainForecastViewHolder(private val binding: MainForecastItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(mainForecast: MainForecast) {
        binding.location.text = mainForecast.location
        Glide.with(binding.root.context).load("https:${mainForecast.condition.icon}").into(binding.icon)
        binding.temperature.text = binding.root.context.getString(R.string.temp_c, mainForecast.temp)
        binding.time.text = mainForecast.localtime
        binding.weatherCondition.text = mainForecast.condition.text
        binding.highestTemp.text = binding.root.context.getString(R.string.highest_temp, mainForecast.maxtemp_c)
        binding.lowestTemp.text = binding.root.context.getString(R.string.lowest, mainForecast.mintemp_c)
    }
}