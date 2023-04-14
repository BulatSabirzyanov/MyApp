package com.example.myapplication.presentation.model


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.WeatherInformation
import com.example.myapplication.domain.repository.WeatherRepository
import com.example.myapplication.domain.usecase.GetWeatherByCityNameUseCase
import com.example.myapplication.domain.usecase.GetWeatherByCoordsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherByCoordsUseCase: GetWeatherByCoordsUseCase
) : ViewModel() {
    private val _temperatureDataState: MutableLiveData<WeatherDataModel> =
        MutableLiveData(null)
    val temperatureDataState: LiveData<WeatherDataModel> = _temperatureDataState
    private val _errorState: MutableLiveData<String> = MutableLiveData("")
    val errorState: LiveData<String> = _errorState


    fun getWeatherByCityName(cityName: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val dateNow = Date().time / 1000
            val response = getWeatherByCityNameUseCase(
                cityName,
                time = (dateNow - weatherRepository.getDateInfoByCityName(
                    cityName
                ))
            )
            Log.e("ошибка во вью модал майн фрагмент", "$response")

            launch {
                runCatching {
                    getWeatherByCityNameUseCase(
                        cityName,
                        time = (dateNow - weatherRepository.getDateInfoByCityName(
                            cityName
                        ))
                    )


                }.onSuccess { weatherDataModel ->

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
                                weatherIcon = weatherDataModel.icon,


                                )
                            weatherRepository.insertWeatherResponse(
                                cachedWeatherResponse
                            )
                        }
                    }


                }.onFailure { exception ->
                    _errorState.postValue("Ошибка при получении погоды: ${exception.localizedMessage}")
                }

            }

        }
    }

    fun getWeatherByCoords(
        latitude: Float,
        longitude: Float,
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            val dateNow = Date().time / 1000


            launch {
                runCatching {
                    getWeatherByCoordsUseCase(
                        latitude = latitude,
                        longitude = longitude,
                        time = (dateNow - weatherRepository.getDateInfoByCoords(
                            latitude = latitude, longitude = longitude
                        ))
                    )


                }.onSuccess { weatherDataModel ->

                    _temperatureDataState.postValue(weatherDataModel)
                    if ((dateNow - weatherRepository.getDateInfoByCoords(
                            latitude = latitude, longitude = longitude
                        )) > 60
                    ) {
                        withContext(Dispatchers.IO) {
                            val date = Date().time / 1000
                            val cachedWeatherResponse = WeatherInformation(
                                id = weatherDataModel.cityName + weatherDataModel.latitude.toString() + weatherDataModel.longitude.toString(),
                                date = date,
                                cityName = weatherDataModel.cityName,
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


                }.onFailure { exception ->
                    _errorState.postValue("Ошибка при получении погоды: ${exception.localizedMessage}")
                }

            }

        }

    }

}