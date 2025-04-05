package com.example.weatherapplication.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.example.weatherapplication.api.RetrofitInstance
import com.example.weatherapplication.api.WeatherAPI
import com.example.weatherapplication.entity.LocationData
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.Locale

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

    fun getLocationName(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())

        return try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                addresses[0].adminArea
            } else {
                "Location not found"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Unable to get location"
        }
    }

    fun getLocationFullName(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())

        return try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val fullAddress = buildString {
                    for (i in 0..addresses[0].maxAddressLineIndex) {
                        append(addresses[0].locality)
                        append(", ")
                        append(addresses[0].adminArea)
                        append(", ")
                        append(addresses[0].countryName)
                    }
                }
                fullAddress.trimEnd(',', ' ')
            } else {
                "Location not found"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Unable to get location"
        }
    }

    suspend fun searchLocation(query: String): List<LocationData> {
        val response = RetrofitInstance.api.searchLocation(location = query)
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }
}