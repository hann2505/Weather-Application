package com.example.weatherapplication.extension

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.Locale

object LocationConverter {

    fun getLocationName(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())

        return try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                addresses[0].locality
            } else {
                "Location not found"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Unable to get location"
        }
    }

    fun getLocationRegion(context: Context, latitude: Double, longitude: Double): String {
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

    fun getLocationCountry(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())

        return try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                addresses[0].countryName
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


}