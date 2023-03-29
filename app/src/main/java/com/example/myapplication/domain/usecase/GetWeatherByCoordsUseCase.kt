package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.entity.mapWeatherEntity
import com.example.myapplication.domain.repository.WeatherRepository
import com.example.myapplication.presentation.model.WeatherDataModel

class GetWeatherByCoordsUseCase(
    private val weatherRepository: WeatherRepository
) {
    /*suspend fun getWeatherByCoords(latitude: Float, longitude: Float): WeatherDataModel {
        return weatherRepository.getWeatherInfoByCoords(latitude = latitude, longitude = longitude)
            .mapWeatherEntity()
    }*/

    suspend operator fun invoke(latitude: Float, longitude: Float, time: Long): WeatherDataModel {
        return weatherRepository.getWeatherInfoByCoords(
            latitude = latitude,
            longitude = longitude,
            time = time
        )
            .mapWeatherEntity()
    }

}
