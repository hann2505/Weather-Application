package com.example.weatherapplication.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.LocationDataRepository
import com.example.weatherapplication.entity.Coordination
import com.example.weatherapplication.entity.LocationData
import com.example.weatherapplication.extension.LocationConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationDataRepository: LocationDataRepository
) : ViewModel() {

    private var _queryLocation = MutableLiveData<List<Coordination>>()
    val queryLocation: LiveData<List<Coordination>> = _queryLocation


    private var _currentLocation = MutableStateFlow("")
    val currentLocation: StateFlow<String> = _currentLocation

    private var _location = MutableLiveData<LocationData>()
    val location: LiveData<LocationData?> = _location

    private var _coordinates = MutableLiveData<Pair<Double, Double>>()
    val coordinates: LiveData<Pair<Double, Double>> = _coordinates

    private fun getLocationName(context: Context, latitude: Double, longitude: Double): String {
        return LocationConverter.getLocationName(context, latitude, longitude)
    }

    private fun getLocationRegion(context: Context, latitude: Double, longitude: Double): String {
        return LocationConverter.getLocationRegion(context, latitude, longitude)
    }

    private fun getLocationCountry(context: Context, latitude: Double, longitude: Double): String {
        return LocationConverter.getLocationCountry(context, latitude, longitude)
    }

    fun getCurrentLocation(context: Context) {
        viewModelScope.launch {
            locationDataRepository.getCurrentLocation(
                context = context,
                onSuccess = { latitude, longitude ->
                    getLocation(context, latitude, longitude)
                },
                onFailure = { exception ->
                    Log.e("MainViewModel", "Error getting current location", exception)
                }
            )
        }
    }

    fun getRemoteLocation(context: Context, latitude: Double, longitude: Double) {
        _currentLocation.value =
            getLocationName(context, latitude = latitude, longitude = longitude)
    }

    fun getLocation(context: Context, latitude: Double, longitude: Double) {
        val locationData = LocationData(
            name = getLocationName(context, latitude, longitude),
            region = getLocationRegion(context, latitude, longitude),
            country = getLocationCountry(context, latitude, longitude),
            localtime = "",
            lat = latitude,
            lon = longitude
        )
        _location.postValue(locationData)
    }

    fun getCurrentLocationCoordinates(context: Context) {
        viewModelScope.launch {
            locationDataRepository.getCurrentLocation(
                context = context,
                onSuccess = { latitude, longitude ->
                    _coordinates.value = Pair(latitude, longitude)
                },
                onFailure = { exception ->
                    Log.e("MainViewModel", "Error getting current location", exception)
                }
            )
        }
    }

    fun searchLocation(query: String) {
        viewModelScope.launch {
            val listLocation = locationDataRepository.searchLocation(query).map {
                Coordination(it.lat, it.lon)
            }
            _queryLocation.postValue(listLocation)
            Log.d("LocationViewModel", "Search location: $listLocation")
        }
    }

}