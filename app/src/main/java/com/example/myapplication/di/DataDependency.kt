package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.OpenWeatherService
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.DatabaseRepository
import com.example.myapplication.data.mappers.WeatherInfoMapper
import com.example.myapplication.data.mappers.WeatherResponseMapper
import com.example.myapplication.data.model.OpenWeatherApiService
import com.example.myapplication.data.repository.WeatherRepositoryImpl
import com.example.myapplication.domain.repository.WeatherRepository
import com.example.myapplication.domain.usecase.GetWeatherByCityNameUseCase
import com.example.myapplication.domain.usecase.GetWeatherByCoordsUseCase


class DataDependency(private val context: Context) {
    private val weatherResponseMapper = WeatherResponseMapper()
    private val weatherInfoMapper = WeatherInfoMapper()
    private val openWeatherApi: OpenWeatherApiService? = OpenWeatherService.getInstance()
    private var databaseRepository: DatabaseRepository =
        DatabaseRepository(AppDatabase.getInstance(context))

    internal val weatherRepository: WeatherRepository = WeatherRepositoryImpl(
        remoteSource = openWeatherApi,
        localSource = databaseRepository,
        weatherResponseMapper = weatherResponseMapper,
        weatherInfoMapper = weatherInfoMapper
    )

    val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase = GetWeatherByCityNameUseCase(
        weatherRepository
    )

    val getWeatherByCoordsUseCase: GetWeatherByCoordsUseCase =
        GetWeatherByCoordsUseCase(weatherRepository)

}