package com.example.weatherapplication.adapter.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.adapter.HourlyForecastAdapter
import com.example.weatherapplication.databinding.HourlyForecastItemBinding
import com.example.weatherapplication.databinding.HourlyForecastWeatherBinding
import com.example.weatherapplication.entity.HourForecast


class HourlyForecastViewHolder(private val binding: HourlyForecastWeatherBinding) : RecyclerView.ViewHolder(binding.root) {

    private val hourlyForecastAdapter = HourlyForecastAdapter()

    private val hourlyForecastList = mutableListOf<HourForecast>()

    fun setupRecyclerView() {
        binding.hourlyForecastRecyclerView.layoutManager = LinearLayoutManager(
            binding.root.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.hourlyForecastRecyclerView.adapter = hourlyForecastAdapter
    }

    fun submitList(list: List<HourForecast>) {
        hourlyForecastList.clear()
        hourlyForecastList.addAll(list)
        hourlyForecastAdapter.submitList(hourlyForecastList)
    }
}