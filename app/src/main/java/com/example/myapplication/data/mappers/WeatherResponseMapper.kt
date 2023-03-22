package com.example.myapplication.data.mappers

import com.example.myapplication.data.model.response.WeatherResponse
import com.example.myapplication.domain.entity.WeatherEntity

class WeatherResponseMapper {
    fun map(item: WeatherResponse?): WeatherEntity {
        return (item?.let { response ->
            with(response) {
                WeatherEntity(
                    temperature = item.main?.temp ?: 0.0f,
                    pressure = item.main?.pressure ?: 0.0f,
                    humidity = item.main?.humidity ?: 0.0f,
                    feelsLike = item.main?.feelsLike ?: 0.0f,
                    speed = item.wind?.speed ?: 0.0f,
                    main = (item.weatherList?.get(1) ?: "") as String,
                    icon = (item.weatherList?.get(3) ?: "") as String,
                    name = item.name ?: ""

                )
            }

        } ?: WeatherEntity(
            temperature = 0.0f,
            pressure = 0.0f,
            humidity = 0.0f,
            feelsLike = 0.0f,
            speed = 0.0f,
            main = "",
            icon = "",
            name = ""
        ))
    }
}