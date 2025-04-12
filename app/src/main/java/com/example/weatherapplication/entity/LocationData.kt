package com.example.weatherapplication.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationData(
    val name: String?,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val localtime: String
): Parcelable
