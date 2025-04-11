package com.example.weatherapplication.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordination(
    val lat: Double,
    val lon: Double
): Parcelable
