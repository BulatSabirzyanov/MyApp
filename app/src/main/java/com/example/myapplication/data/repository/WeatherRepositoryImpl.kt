package com.example.myapplication.data.repository

import com.example.myapplication.data.db.DatabaseRepository
import com.example.myapplication.data.mappers.WeatherResponseMapper
import com.example.myapplication.data.model.OpenWeatherApiService
import com.example.myapplication.domain.entity.WeatherEntity
import com.example.myapplication.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val remoteSource: OpenWeatherApiService?,
    private val localSource: DatabaseRepository,
    private val weatherResponseMapper: WeatherResponseMapper
) : WeatherRepository {

    override suspend fun getWeatherInfoByCityName(city: String): WeatherEntity {
        return withContext(Dispatchers.IO) {
            (weatherResponseMapper::map)(remoteSource?.getWeatherByCityName(city = city))


        }
    }

    override suspend fun getWeatherInfoByCoords(
        latitude: String,
        longitude: String
    ): WeatherEntity {
        return withContext(Dispatchers.IO) {
            (weatherResponseMapper::map)(
                remoteSource?.getWeatherByCityLocation(
                    latitude = latitude,
                    longitude = longitude
                )
            )

        }


    }


}