package com.example.weatherapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.adapter.viewholder.DailyForecastViewHolder
import com.example.weatherapplication.adapter.viewholder.HourlyForecastViewHolder
import com.example.weatherapplication.adapter.viewholder.MainForecastViewHolder
import com.example.weatherapplication.databinding.DailyForecastWeatherBinding
import com.example.weatherapplication.databinding.HourlyForecastWeatherBinding
import com.example.weatherapplication.databinding.MainForecastItemBinding
import com.example.weatherapplication.entity.DayForecast
import com.example.weatherapplication.entity.Forecast
import com.example.weatherapplication.entity.forecast.MainForecast

class WeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Any>()

    companion object {
        private const val TYPE_MAIN_FORECAST = 0
        private const val TYPE_HOURLY_FORECAST = 1
        private const val TYPE_DAILY_FORECAST = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_MAIN_FORECAST -> MainForecastViewHolder(
                MainForecastItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_HOURLY_FORECAST -> HourlyForecastViewHolder(
                HourlyForecastWeatherBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_DAILY_FORECAST -> DailyForecastViewHolder(
                DailyForecastWeatherBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainForecastViewHolder -> {
                holder.bindData(items[position] as MainForecast)
            }
            is HourlyForecastViewHolder -> {
                holder.setupRecyclerView()
                holder.submitList((items[position] as DayForecast).hour)
            }
            is DailyForecastViewHolder -> {
                holder.bindData(items[position] as Forecast)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is MainForecast -> TYPE_MAIN_FORECAST
            is DayForecast -> TYPE_HOURLY_FORECAST
            is Forecast -> TYPE_DAILY_FORECAST
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    fun addFirst(item: Any) {
        items.clear()
        items.add(item)
        notifyDataSetChanged()
    }

    fun addSecond(item: Any) {
        items.add(1, item)
        notifyDataSetChanged()
    }

    fun addLast(item: Any) {
        items.add(item)
        notifyDataSetChanged()
    }

}