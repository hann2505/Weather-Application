package com.example.weatherapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.HourlyForecastItemBinding
import com.example.weatherapplication.entity.HourForecast
import com.example.weatherapplication.extension.TimeConverter

class HourlyForecastAdapter : RecyclerView.Adapter<HourlyForecastAdapter.MyViewHolder>() {

    private val hourlyForecastList = mutableListOf<HourForecast>()

    inner class MyViewHolder(private val binding: HourlyForecastItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(hourForecast: HourForecast) {
            binding.time.text = TimeConverter.localDateTime(hourForecast.time).hour.toString()
            Glide.with(binding.root.context).load("https:${hourForecast.condition.icon}").into(binding.icon)
            binding.temp.text = binding.root.context.getString(R.string.temp_c, hourForecast.temperature)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyForecastAdapter.MyViewHolder {
        return MyViewHolder(HourlyForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HourlyForecastAdapter.MyViewHolder, position: Int) {
        holder.bindData(hourlyForecastList[position])
    }

    override fun getItemCount() = hourlyForecastList.size

    fun submitList(list: List<HourForecast>) {
        hourlyForecastList.clear()
        hourlyForecastList.addAll(list)
        notifyDataSetChanged()
    }
}