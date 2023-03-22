package com.example.myapplication.di

import com.example.myapplication.data.OpenWeatherService
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.DatabaseRepository
import com.example.myapplication.data.mappers.WeatherResponseMapper
import com.example.myapplication.data.model.OpenWeatherApiService
import com.example.myapplication.data.repository.WeatherRepositoryImpl
import com.example.myapplication.domain.repository.WeatherRepository
import com.example.myapplication.domain.usecase.GetWeatherByCityNameUseCase


object DataDependency {
    private val weatherResponseMapper = WeatherResponseMapper()
    val openWeatherApi: OpenWeatherApiService? = OpenWeatherService.getInstance()
    private var databaseRepository = DatabaseRepository(AppDatabase.getInstance(context))

    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl(
        remoteSource = openWeatherApi,
        localSource = databaseRepository,
        weatherResponseMapper = weatherResponseMapper,

        )

    val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase = GetWeatherByCityNameUseCase(
        weatherRepository
    )

}