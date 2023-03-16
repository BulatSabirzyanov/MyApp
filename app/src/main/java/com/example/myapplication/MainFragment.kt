package com.example.myapplication


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.DatabaseRepository
import com.example.myapplication.data.db.WeatherInfo
import com.example.myapplication.data.model.response.WeatherResponse
import com.example.myapplication.data.repository.WeatherRepository
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.utils.hideKeyboard
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var cityName: String
    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var weatherResponse: WeatherResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseRepository = DatabaseRepository(AppDatabase.getInstance(requireContext()))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainBinding.bind(view)
        val repository = WeatherRepository()

        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener {
            view.hideKeyboard()
        }
        with(binding) {
            btnSearch.setOnClickListener {
                cityName =
                    editTextTextPersonName.text.toString().replaceFirstChar { it.uppercaseChar() }
                lifecycleScope.launch(Dispatchers.IO) {
                    val cityNames = databaseRepository.getAllCityNames()
                    val dateNow = Date().time / 1000

                    if (cityNames.contains(cityName) && ((dateNow - databaseRepository.getDateInfoByCityName(
                            cityName
                        )) < 60)
                    ) {

                        val weatherInfo = databaseRepository.getCachedWeatherResponse(cityName)
                        Log.e(
                            "ado", "$dateNow - ${
                                databaseRepository.getDateInfoByCityName(
                                    cityName
                                )
                            }"
                        )
                        val cityName = weatherInfo.cityName
                        val temp = weatherInfo.temp
                        val src = weatherInfo.weatherIcon
                        withContext(Dispatchers.Main) {
                            tVCityNameMainFragment.text = cityName
                            tVTempMainFragment.text = "$temp\u00B0C"
                            Glide.with(this@MainFragment).load(
                                "https://openweathermap.org/img/wn/$src.png"
                            ).into(iVWeatherIconMainFragment)

                        }

                    } else {
                        lifecycleScope.launch {
                            runCatching {

                                weatherResponse =
                                    repository.getWeatherInfoByCityName(city = cityName)

                                Log.e("12321", "$weatherResponse")
                            }.onSuccess {

                                val temp = weatherResponse?.main?.temp?.toInt().toString()
                                val src = weatherResponse?.weatherList?.get(0)?.icon

                                withContext(Dispatchers.Main) {
                                    tVCityNameMainFragment.text = cityName
                                    tVTempMainFragment.text = "$temp\u00B0C"
                                    Glide.with(this@MainFragment).load(
                                        "https://openweathermap.org/img/wn/$src.png"
                                    ).into(iVWeatherIconMainFragment)
                                }
                                withContext(Dispatchers.IO) {
                                    val date = Date().time / 1000
                                    val cachedWeatherResponse = WeatherInfo(
                                        date = date,
                                        cityName = cityName,
                                        latitude = weatherResponse?.coords?.latitude,
                                        longitude = weatherResponse?.coords?.longitude,
                                        temp = weatherResponse?.main?.temp,
                                        feelsLike = weatherResponse?.main?.feelsLike,
                                        tempMin = weatherResponse?.main?.tempMin,
                                        tempMax = weatherResponse?.main?.tempMax,
                                        pressure = weatherResponse?.main?.pressure,
                                        humidity = weatherResponse?.main?.humidity,
                                        windSpeed = weatherResponse?.wind?.speed,
                                        weatherId = weatherResponse?.weatherList?.getOrNull(0)?.id,
                                        weatherMain = weatherResponse?.weatherList?.getOrNull(0)?.main,
                                        weatherDescription = weatherResponse?.weatherList?.getOrNull(
                                            0
                                        )?.description,
                                        weatherIcon = weatherResponse?.weatherList?.getOrNull(0)?.icon
                                    )
                                    databaseRepository.insertWeatherResponse(cachedWeatherResponse)
                                }


                            }.onFailure {
                                view.hideKeyboard()
                                Toast.makeText(
                                    requireContext(),
                                    "такого города не существует",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }

                    }
                }


            }


            btnGeo.setOnClickListener {
                view.hideKeyboard()
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return@setOnClickListener
                }
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->

                        val latitude = location!!.latitude.toFloat()
                        val longitude = location.longitude.toFloat()
                        lifecycleScope.launch(Dispatchers.IO) {
                            val dateNow = System.currentTimeMillis() / 1000
                            if ((dateNow - databaseRepository.getDateInfoByCoords(
                                    latitude = latitude, longitude = longitude
                                )) > 60
                            ) {
                                Log.e(
                                    "зашел в условие правильно", "$dateNow - ${
                                        databaseRepository.getDateInfoByCoords(
                                            latitude = latitude, longitude = longitude
                                        )
                                    }"
                                )

                                lifecycleScope.launch {
                                    weatherResponse = repository.getWeatherInfoByCityLocation(
                                        latitude = latitude.toString(),
                                        longitude = longitude.toString()
                                    )
                                    val src = weatherResponse?.weatherList?.get(0)?.icon
                                    cityName = weatherResponse?.name.toString()
                                        .replaceFirstChar { it.uppercaseChar() }
                                    withContext(Dispatchers.Main) {
                                        tVTempMainFragment.text =
                                            weatherResponse?.main?.temp?.toInt().toString()
                                        tVCityNameMainFragment.text =
                                            weatherResponse?.name.toString()
                                        Glide.with(this@MainFragment).load(
                                            "https://openweathermap.org/img/wn/$src.png"
                                        ).into(iVWeatherIconMainFragment)
                                    }
                                    withContext(Dispatchers.IO) {

                                        val cachedWeatherResponse = WeatherInfo(
                                            date = dateNow,
                                            cityName = cityName,
                                            latitude = weatherResponse?.coords?.latitude,
                                            longitude = weatherResponse?.coords?.longitude,
                                            temp = weatherResponse?.main?.temp,
                                            feelsLike = weatherResponse?.main?.feelsLike,
                                            tempMin = weatherResponse?.main?.tempMin,
                                            tempMax = weatherResponse?.main?.tempMax,
                                            pressure = weatherResponse?.main?.pressure,
                                            humidity = weatherResponse?.main?.humidity,
                                            windSpeed = weatherResponse?.wind?.speed,
                                            weatherId = weatherResponse?.weatherList?.getOrNull(0)?.id,
                                            weatherMain = weatherResponse?.weatherList?.getOrNull(0)?.main,
                                            weatherDescription = weatherResponse?.weatherList?.getOrNull(
                                                0
                                            )?.description,
                                            weatherIcon = weatherResponse?.weatherList?.getOrNull(0)?.icon
                                        )
                                        databaseRepository.insertWeatherResponse(
                                            cachedWeatherResponse
                                        )
                                    }
                                }

                            } else {
                                Log.e("зашел в условие неправильно", "321")
                                val weatherInfo = databaseRepository.getWeatherInfoByCoords(
                                    latitude = latitude.toFloat(),
                                    longitude = longitude.toFloat()
                                )

                                Log.e(
                                    "", "${
                                        databaseRepository.getWeatherInfoByCoords(
                                            latitude = latitude,
                                            longitude = longitude
                                        )
                                    }"
                                )
                                cityName = weatherInfo.cityName
                                val src = weatherInfo.weatherIcon
                                val temp = weatherInfo.temp
                                withContext(Dispatchers.Main) {
                                    tVTempMainFragment.text =
                                        temp?.toInt().toString()
                                    tVCityNameMainFragment.text = cityName
                                    Glide.with(this@MainFragment).load(
                                        "https://openweathermap.org/img/wn/$src.png"
                                    ).into(iVWeatherIconMainFragment)
                                }


                            }

                        }


                    }
            }
            tVCityNameMainFragment.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("cityName", cityName)
                }
                findNavController(view).navigate(
                    R.id.action_mainFragment_to_weatherPageFragment, bundle
                )
            }
        }


    }


    companion object {
        @JvmStatic
        fun getInstance() = MainFragment()
    }
}