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
class MainViewModel @Inject constructor(
    private val locationDataRepository: LocationDataRepository
) : ViewModel() {

    private var _currentLocation = MutableStateFlow("")
    val currentLocation: StateFlow<String> = _currentLocation

    private fun getLocationName(context: Context, latitude: Double, longitude: Double): String {
        return locationDataRepository.getLocationName(context, latitude, longitude)
    }

    fun getCurrentLocation(context: Context) {
        viewModelScope.launch {
            locationDataRepository.getCurrentLocation(
                context = context,
                onSuccess = { latitude, longitude ->
                    _currentLocation.value =
                        getLocationName(context, latitude = latitude, longitude = longitude)
                },
                onFailure = { exception ->
                    Log.e("MainViewModel", "Error getting current location", exception)
                }
            )
        }
    }

}