package com.example.weatherapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.LocationDataRepository
import com.example.weatherapplication.data.WeatherDataRepository
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

    fun getWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val weatherData = weatherDataRepository.getWeatherData(latitude, longitude)
            _weatherData.postValue(weatherData)
            Log.d("MainViewModel", "Weather data: $weatherData")
        }
    }
}