package com.example.weatherapplication.module

import com.example.weatherapplication.data.LocationDataRepository
import com.example.weatherapplication.data.WeatherDataRepository
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
    fun provideLocationDataRepository() = LocationDataRepository()

    @Provides
    @Singleton
    fun provideWeatherDataRepository() = WeatherDataRepository()
}