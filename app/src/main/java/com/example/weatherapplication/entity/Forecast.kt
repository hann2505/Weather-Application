package com.example.weatherapplication.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecast (
    @SerializedName("forecastday") val forecastDay: List<DayForecast>
): Parcelable