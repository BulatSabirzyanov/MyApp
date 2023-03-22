package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.entity.mapWeatherEntity
import com.example.myapplication.domain.repository.WeatherRepository
import com.example.myapplication.presentation.model.WeatherDataModel


class GetWeatherByCityNameUseCase(
    private val weatherRepository: WeatherRepository
) {
    /*suspend fun getWeatherByCityName(city: String): WeatherDataModel {
        return weatherRepository.getWeatherInfoByCityName(city = city).mapWeatherEntity()
    }*/
    suspend operator fun invoke(city: String): WeatherDataModel {
        return weatherRepository.getWeatherInfoByCityName(city).mapWeatherEntity()
    }


}