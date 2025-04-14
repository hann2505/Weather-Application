package com.example.weatherapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.DailyForecastItemBinding
import com.example.weatherapplication.entity.DayForecast
import com.example.weatherapplication.extension.TimeConverter

class DailyForecastAdapter : RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder>() {

    private val dailyForecastList = mutableListOf<DayForecast>()

    inner class DailyForecastViewHolder(private val binding: DailyForecastItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(dailyForecast: DayForecast) {
            binding.day.text = TimeConverter.getDayOfWeek(dailyForecast.date)
            Glide.with(binding.root.context).load("https:${dailyForecast.day.condition.icon}").into(binding.icon)
            binding.chanceOfRain.text = binding.root.context.getString(R.string.ratio, dailyForecast.day.chanceOfRain)
            binding.lowestTemp.text = dailyForecast.day.mintemp_c.toString()
            binding.highestTemp.text = dailyForecast.day.maxtemp_c.toString()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyForecastAdapter.DailyForecastViewHolder {
        return DailyForecastViewHolder(DailyForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: DailyForecastAdapter.DailyForecastViewHolder,
        position: Int
    ) {
        holder.bindData(dailyForecastList[position])
    }

    override fun getItemCount() = dailyForecastList.size

    fun submitList(newList: List<DayForecast>) {
        dailyForecastList.clear()
        dailyForecastList.addAll(newList)
        notifyDataSetChanged()
    }

}