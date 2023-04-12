package com.example.myapplication.presentation.model


import android.util.Log
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

    private val _temperatureDataState: MutableLiveData<WeatherDataModel> =
        MutableLiveData(null)
    val temperatureDataState: LiveData<WeatherDataModel> = _temperatureDataState
    fun getWeatherByCityName(cityName: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val dateNow = Date().time / 1000
            val response = getWeatherByCityNameUseCase(
                cityName,
                time = (dateNow - weatherRepository.getDateInfoByCityName(
                    cityName
                ))
            )
            Log.e("ошибка во вью модал майн фрагмент", "${response}")

            launch {
                runCatching {
                    getWeatherByCityNameUseCase(
                        cityName,
                        time = (dateNow - weatherRepository.getDateInfoByCityName(
                            cityName
                        ))
                    )



                }.onSuccess { weatherDataModel ->
                    Log.e("ошибка во вью модал майн фрагмент", "$weatherDataModel")
                    _temperatureDataState.postValue(weatherDataModel)
                    if ((dateNow - weatherRepository.getDateInfoByCityName(
                            cityName
                        )) > 60
                    ) {
                        withContext(Dispatchers.IO) {
                            val date = Date().time / 1000
                            val cachedWeatherResponse = WeatherInformation(
                                id = cityName + weatherDataModel.latitude.toString() + weatherDataModel.longitude.toString(),
                                date = date,
                                cityName = cityName,
                                latitude = weatherDataModel.latitude,
                                longitude = weatherDataModel.longitude,
                                temp = weatherDataModel.temperature,
                                feelsLike = weatherDataModel.feelsLike,
                                pressure = weatherDataModel.pressure,
                                humidity = weatherDataModel.humidity,
                                windSpeed = weatherDataModel.speed,
                                weatherMain = weatherDataModel.main,
                                weatherIcon = weatherDataModel.icon
                            )
                            weatherRepository.insertWeatherResponse(
                                cachedWeatherResponse
                            )
                        }
                    }


                }.onFailure {
                }

            }
            launch {
                runCatching {
                    getWeatherForecastByCityNameUseCase(cityName)
                }.onSuccess { WeatherForecastResponse ->


                }.onFailure {

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