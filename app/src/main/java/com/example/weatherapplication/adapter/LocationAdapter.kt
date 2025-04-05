package com.example.weatherapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.MyLocationItemBinding
import com.example.weatherapplication.databinding.SearchItemBinding
import com.example.weatherapplication.entity.LocationData

class LocationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val items = mutableListOf<Any>()

    companion object {
        private const val TYPE_USER_ITEM = 0
        private const val TYPE_SEARCH_ITEM = 1
    }

    inner class UserItemViewHolder(
        private val binding: MyLocationItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindData(locationData: LocationData) {
            val locationName = "${locationData.name}, ${locationData.region}, ${locationData.country}"
            binding.location.text = locationName
        }
    }

    inner class SearchItemViewHolder(
        private val binding: SearchItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindData(locationName: String) {
            binding.searchLocation.text = locationName
            Log.d("LocationAdapter", "Search: $locationName")
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
            is UserItemViewHolder -> holder.bindData(items[position] as LocationData)
            is SearchItemViewHolder -> holder.bindData(items[position] as String)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is LocationData -> TYPE_USER_ITEM
            is String -> TYPE_SEARCH_ITEM
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    fun submitList(newList: List<Any>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

}