package com.example.weatherapplication.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Day (
    @SerializedName("daily_chance_of_rain")
    val chanceOfRain: Int,
    val maxtemp_c: Double,
    val mintemp_c: Double,
): Parcelable