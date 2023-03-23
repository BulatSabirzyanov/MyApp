package com.example.myapplication.domain.repository

import com.example.myapplication.domain.entity.WeatherEntity

interface WeatherRepository {

    suspend fun getWeatherInfoByCityName(city: String, time: Long): WeatherEntity


    suspend fun getWeatherInfoByCoords(latitude: Float, longitude: Float, time: Long): WeatherEntity

    suspend fun getAllCityNames()

    suspend fun insertWeatherResponse(cachedWeatherResponse: com.example.myapplication.data.db.WeatherInformation)

    suspend fun getDateInfoByCityName(cityName: String): Long

    suspend fun getDateInfoByCoords(latitude: Float, longitude: Float): Long
}