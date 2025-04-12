package com.example.weatherapplication.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.weatherapplication.api.RetrofitInstance
import com.example.weatherapplication.entity.LocationSearchData
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class LocationDataRepository {

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        context: Context,
        onSuccess: (latitude: Double, longitude: Double) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            onSuccess(
                location.latitude,
                location.longitude
            )
            Log.d("WeatherDataRepository", "Current location: $location")
        }.addOnFailureListener { exception ->
            onFailure(exception)
            Log.e("WeatherDataRepository", "Error getting current location", exception)
        }
    }

    suspend fun searchLocation(query: String): List<LocationSearchData> {
        val response = RetrofitInstance.api.searchLocation(location = query)
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }
}