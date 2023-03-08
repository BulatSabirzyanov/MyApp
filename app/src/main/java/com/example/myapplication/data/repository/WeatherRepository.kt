package com.example.myapplication.data.repository

import com.example.myapplication.data.OpenWeatherService
import com.example.myapplication.data.model.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {

    suspend fun getWeatherInfoByCityName(city: String): WeatherResponse? {
        return withContext(Dispatchers.IO) {
            OpenWeatherService.getInstance()?.getWeatherByCityName(city = city)
        }
    }

    suspend fun getWeatherInfoByCityLocation(
        latitude: String,
        longitude: String
    ): WeatherResponse? {
        return withContext(Dispatchers.IO) {
            OpenWeatherService.getInstance()?.getWeatherByCityLocation(latitude, longitude)
        }

    }


}