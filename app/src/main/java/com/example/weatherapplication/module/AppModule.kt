package com.example.weatherapplication.module

import com.example.weatherapplication.api.WeatherAPI
import com.example.weatherapplication.data.LocationDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherDataRepository() = LocationDataRepository()
}