package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.response.WeatherForecastResponse
import com.example.myapplication.domain.entity.WeatherEntity
import com.example.myapplication.presentation.model.WeatherFullInfo
import javax.inject.Singleton


@Singleton
interface WeatherRepository {

    suspend fun getWeatherInfoByCityName(city: String, time: Long): WeatherEntity


    suspend fun getWeatherInfoByCoords(latitude: Float, longitude: Float, time: Long): WeatherEntity

    suspend fun getAllCityNames()

    suspend fun insertWeatherResponse(cachedWeatherResponse: com.example.myapplication.data.db.WeatherInformation)

    suspend fun getDateInfoByCityName(cityName: String): Long

    suspend fun getDateInfoByCoords(latitude: Float, longitude: Float): Long

    suspend fun getForecastWeatherInfo(city: String): WeatherForecastResponse

    suspend fun getWeatherInfo(city: String, time: Long): WeatherFullInfo
}