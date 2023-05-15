package com.example.myapplication.domain.entity

import com.example.myapplication.presentation.model.WeatherDataModel

data class WeatherEntity(
    val date: Long = 0,
    val temperature: Float,
    val pressure: Float,
    val humidity: Float,
    val feelsLike: Float,
    val latitude: Float?,
    val longitude: Float?,
    val speed: Float,
    val main: String,
    val icon: String,
    val cityName: String
)

fun WeatherEntity.mapWeatherEntity(): WeatherDataModel {
    return WeatherDataModel(
        date = this.date,
        temperature = this.temperature,
        pressure = this.pressure,
        humidity = this.humidity,
        feelsLike = this.feelsLike,
        latitude = this.latitude,
        longitude = this.longitude,
        speed = this.speed,
        main = this.main,
        icon = this.icon,
        cityName = this.cityName
    )
}
