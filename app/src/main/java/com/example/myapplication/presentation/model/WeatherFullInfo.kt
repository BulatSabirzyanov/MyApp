package com.example.myapplication.presentation.model

import com.example.myapplication.data.model.response.WeatherForecastResponse
import com.example.myapplication.domain.entity.WeatherEntity

data class WeatherFullInfo(
    val weatherEntity: WeatherEntity,
    val weatherForecastResponse: WeatherForecastResponse
)
