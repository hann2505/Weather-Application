package com.example.weatherapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.LocationDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationDataRepository: LocationDataRepository
) : ViewModel() {

    private var _queryLocation = MutableStateFlow(emptyList<String>())
    val queryLocation: StateFlow<List<String>> = _queryLocation

    private fun getLocationFullName(context: Context, latitude: Double, longitude: Double): String {
        return locationDataRepository.getLocationFullName(context, latitude, longitude)
    }

    fun searchLocation(context: Context, query: String) {
        viewModelScope.launch {
            val listLocation = locationDataRepository.searchLocation(query).map {
                getLocationFullName(context, it.lat, it.lon)
            }
            _queryLocation.value = listLocation
            Log.d("LocationViewModel", "Search location: $listLocation")
        }
    }

}