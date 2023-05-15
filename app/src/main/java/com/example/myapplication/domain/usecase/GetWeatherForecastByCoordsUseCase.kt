package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.response.WeatherForecastResponse
import com.example.myapplication.domain.repository.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeatherForecastByCityNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: String): WeatherForecastResponse {
        return weatherRepository.getForecastWeatherInfo(
            city = city
        )
    }
}