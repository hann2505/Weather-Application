package com.example.weatherapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.R
import com.example.weatherapplication.data.LocationDataRepository
import com.example.weatherapplication.data.WeatherDataRepository
import com.example.weatherapplication.entity.LocationSearchData
import com.example.weatherapplication.entity.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) : ViewModel() {
    private var _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData

    private var _weatherDataList = MutableLiveData<List<WeatherData>>()
    val weatherDataList: LiveData<List<WeatherData>> = _weatherDataList

    fun getWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val weatherData = weatherDataRepository.getWeatherData(latitude, longitude)
            _weatherData.postValue(weatherData)
            Log.d("MainViewModel", "Weather data: $weatherData")
        }
    }

    fun getWeatherDataList(searchLocations: List<LocationSearchData>) {
        viewModelScope.launch {
            val weatherDataList = searchLocations.map { location ->
                weatherDataRepository.getWeatherData(location.lat, location.lon)
            }
            _weatherDataList.postValue(weatherDataList)
            Log.d("MainViewModel", "Weather data list: $weatherDataList")
        }
    }
}