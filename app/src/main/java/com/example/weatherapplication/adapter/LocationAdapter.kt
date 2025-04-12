package com.example.weatherapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.MyLocationItemBinding
import com.example.weatherapplication.databinding.SearchItemBinding
import com.example.weatherapplication.entity.WeatherData
import com.example.weatherapplication.entity.forecast.UserItem
import com.example.weatherapplication.extension.LocationConverter

class LocationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val items = mutableListOf<Any>()

    companion object {
        private const val TYPE_USER_ITEM = 0
        private const val TYPE_SEARCH_ITEM = 1
    }

    private var onItemClickListener: ((WeatherData) -> Unit)? = null

    fun setOnItemClickListener(listener: (WeatherData) -> Unit) {
        onItemClickListener = listener
    }

    inner class UserItemViewHolder(
        private val binding: MyLocationItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindData(userItem: UserItem) {

        }
    }

    inner class SearchItemViewHolder(
        private val binding: SearchItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindData(weatherData: WeatherData) {
            binding.searchLocation.text = LocationConverter.getLocationFullName(
                binding.root.context,
                weatherData.location.lat,
                weatherData.location.lon
            )
            Log.d("LocationAdapter", "Search: ${weatherData.location}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_USER_ITEM -> UserItemViewHolder(
                MyLocationItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_SEARCH_ITEM -> SearchItemViewHolder(
                SearchItemBinding.inflate(
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
            is UserItemViewHolder -> {
                holder.bindData(items[position] as UserItem)
            }
            is SearchItemViewHolder -> {
                holder.bindData(items[position] as WeatherData)
                holder.itemView.setOnClickListener {
                    onItemClickListener?.let {
                        it((items[position] as WeatherData))
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is WeatherData -> TYPE_SEARCH_ITEM
            is UserItem -> TYPE_USER_ITEM
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    fun submitList(newList: List<Any>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    fun emptyList() {
        items.clear()
        items.add(emptyList())
        notifyDataSetChanged()
    }

}