package com.example.myapplication.data.mappers


import com.example.myapplication.data.db.WeatherInformation
import com.example.myapplication.domain.entity.WeatherEntity

class WeatherInfoMapper {

    fun map(item: WeatherInformation?): WeatherEntity {
        return (item?.let { response ->
            with(response) {
                WeatherEntity(
                    temperature = item.temp ?: 0.0f,
                    pressure = item.pressure ?: 0.0f,
                    humidity = item.humidity ?: 0.0f,
                    feelsLike = item.feelsLike ?: 0.0f,
                    latitude = item.latitude ?: 0.0f,
                    longitude = item.longitude ?: 0.0f,
                    speed = item.windSpeed ?: 0.0f,
                    main = item.weatherMain ?: "",
                    icon = item.weatherIcon ?: "",
                    cityName = item.cityName

                )
            }

        } ?: WeatherEntity(
            temperature = 0.0f,
            pressure = 0.0f,
            humidity = 0.0f,
            latitude = 0.0f,
            longitude = 0.0f,
            feelsLike = 0.0f,
            speed = 0.0f,
            main = "",
            icon = "",
            cityName = ""
        ))
    }
}
