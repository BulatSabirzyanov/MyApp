package com.example.myapplication.data.repository

import com.example.myapplication.data.db.DatabaseRepository
import com.example.myapplication.data.mappers.WeatherInfoMapper
import com.example.myapplication.data.mappers.WeatherResponseMapper
import com.example.myapplication.data.model.OpenWeatherApiService
import com.example.myapplication.data.model.response.WeatherForecastResponse
import com.example.myapplication.domain.entity.WeatherEntity
import com.example.myapplication.domain.repository.WeatherRepository
import com.example.myapplication.presentation.model.WeatherFullInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteSource: OpenWeatherApiService?,
    private val localSource: DatabaseRepository,
    private val weatherResponseMapper: WeatherResponseMapper,
    private val weatherInfoMapper: WeatherInfoMapper
) : WeatherRepository {


    override suspend fun getWeatherInfoByCityName(city: String, time: Long): WeatherEntity {
        return if (time > 60) {
            withContext(Dispatchers.IO) {
                (weatherResponseMapper::map)(remoteSource?.getWeatherByCityName(city = city))


            }
        } else {
            withContext(Dispatchers.IO) {
                (weatherInfoMapper::map)(localSource.getCachedWeatherResponse(cityName = city))


            }

        }
    }


    override suspend fun getWeatherInfoByCoords(
        latitude: Float,
        longitude: Float,
        time: Long
    ): WeatherEntity {
        return if (time > 60) {
            withContext(Dispatchers.IO) {
                (weatherResponseMapper::map)(
                    remoteSource?.getWeatherByCityLocation(
                        latitude.toString(),
                        longitude.toString()
                    )
                )


            }
        } else {
            withContext(Dispatchers.IO) {
                (weatherInfoMapper::map)(
                    localSource.getWeatherInfoByCoords(
                        latitude,
                        longitude
                    )
                )
            }

        }
    }

    override suspend fun getAllCityNames() {
        return withContext(Dispatchers.IO) {
            localSource.getAllCityNames()
        }
    }

    override suspend fun insertWeatherResponse(cachedWeatherResponse: com.example.myapplication.data.db.WeatherInformation) {
        return withContext(Dispatchers.IO) {
            localSource.insertWeatherResponse(cachedWeatherResponse)
        }
    }

    override suspend fun getDateInfoByCityName(cityName: String): Long {
        return withContext(Dispatchers.IO) {
            localSource.getDateInfoByCityName(cityName = cityName)
        }
    }

    override suspend fun getDateInfoByCoords(latitude: Float, longitude: Float): Long {
        return withContext(Dispatchers.IO) {
            localSource.getDateInfoByCoords(latitude = latitude, longitude = longitude)
        }
    }


    override suspend fun getForecastWeatherInfo(
        city: String
    ): WeatherForecastResponse {
        return withContext(Dispatchers.IO) {
            remoteSource!!.getForecastByCityName(city = city)
        }
    }

    override suspend fun getWeatherInfo(city: String, time: Long): WeatherFullInfo {
        return withContext(Dispatchers.IO) {
            WeatherFullInfo(
                getWeatherInfoByCityName(city, time),
                remoteSource!!.getForecastByCityName(city)
            )
        }

    }


}