package com.example.weatherapplication.adapter.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.adapter.DailyForecastAdapter
import com.example.weatherapplication.databinding.DailyForecastWeatherBinding
import com.example.weatherapplication.entity.DayForecast
import com.example.weatherapplication.entity.Forecast

class DailyForecastViewHolder(private val binding: DailyForecastWeatherBinding) : RecyclerView.ViewHolder(binding.root) {

    private val dailyForecastAdapter = DailyForecastAdapter()


    fun submitList(dailyList: List<DayForecast>) {
        dailyForecastAdapter.submitList(dailyList)
    }

    fun setupRecyclerView() {
        binding.dailyForecastRecyclerView.layoutManager = LinearLayoutManager(
            binding.root.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.dailyForecastRecyclerView.adapter = dailyForecastAdapter
    }
}