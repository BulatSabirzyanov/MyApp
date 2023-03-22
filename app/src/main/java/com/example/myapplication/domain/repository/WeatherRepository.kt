package com.example.myapplication.domain.repository

import com.example.myapplication.domain.entity.WeatherEntity

interface WeatherRepository {

    suspend fun getWeatherInfoByCityName(city: String): WeatherEntity


    suspend fun getWeatherInfoByCoords(latitude: Float, longitude: Float): WeatherEntity


}