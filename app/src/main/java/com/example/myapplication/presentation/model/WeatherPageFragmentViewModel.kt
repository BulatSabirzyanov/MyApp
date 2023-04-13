package com.example.myapplication.presentation.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.WeatherInformation
import com.example.myapplication.domain.repository.WeatherRepository
import com.example.myapplication.domain.usecase.GetWeatherByCityNameUseCase
import com.example.myapplication.domain.usecase.GetWeatherForecastByCityNameUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class WeatherPageFragmentViewModel @AssistedInject constructor(
    private val weatherRepository: WeatherRepository,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherForecastByCityNameUseCase: GetWeatherForecastByCityNameUseCase,
    @Assisted(ASSISTED_CITY_NAME) private val cityName: String

) : ViewModel() {


    private val _fullInfoState: MutableLiveData<WeatherFullInfo> =
        MutableLiveData(null)
    val fullInfoState: LiveData<WeatherFullInfo> = _fullInfoState
    fun getWeatherByCityName(cityName: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val dateNow = Date().time / 1000


            launch {
                runCatching {
                    weatherRepository.getWeatherInfo(
                        cityName, (dateNow - weatherRepository.getDateInfoByCityName(
                            cityName
                        ))
                    )


                }.onSuccess { weatherFullInfo ->

                    _fullInfoState.postValue(weatherFullInfo)
                    if ((dateNow - weatherRepository.getDateInfoByCityName(
                            cityName
                        )) > 60
                    ) {
                        withContext(Dispatchers.IO) {
                            val date = Date().time / 1000
                            val cachedWeatherResponse = WeatherInformation(
                                id = cityName + weatherFullInfo.weatherEntity.latitude.toString() + weatherFullInfo.weatherEntity.longitude.toString(),
                                date = date,
                                cityName = cityName,
                                latitude = weatherFullInfo.weatherEntity.latitude,
                                longitude = weatherFullInfo.weatherEntity.longitude,
                                temp = weatherFullInfo.weatherEntity.temperature,
                                feelsLike = weatherFullInfo.weatherEntity.feelsLike,
                                pressure = weatherFullInfo.weatherEntity.pressure,
                                humidity = weatherFullInfo.weatherEntity.humidity,
                                windSpeed = weatherFullInfo.weatherEntity.speed,
                                weatherMain = weatherFullInfo.weatherEntity.main,
                                weatherIcon = weatherFullInfo.weatherEntity.icon
                            )
                            weatherRepository.insertWeatherResponse(
                                cachedWeatherResponse
                            )
                        }
                    }


                }

            }


        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(ASSISTED_CITY_NAME) cityName: String
        ): WeatherPageFragmentViewModel
    }

    companion object {
        const val ASSISTED_CITY_NAME = "LATITUDE"

    }
}