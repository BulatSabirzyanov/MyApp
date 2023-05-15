package com.example.myapplication.presentation.model

data class WeatherDataModel(
    val date: Long = 0,
    val temperature: Float,
    val latitude: Float?,
    val longitude: Float?,
    val pressure: Float,
    val humidity: Float,
    val feelsLike: Float,
    val speed: Float,
    val main: String,
    val icon: String,
    val cityName: String
)


