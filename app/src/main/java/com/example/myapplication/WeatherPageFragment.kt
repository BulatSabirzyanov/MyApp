package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.DatabaseRepository
import com.example.myapplication.data.model.response.WeatherResponse
import com.example.myapplication.data.repository.WeatherRepository
import com.example.myapplication.databinding.FragmentWeatherPageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class WeatherPageFragment : BottomSheetDialogFragment(R.layout.fragment_weather_page) {

    private lateinit var databaseRepository: DatabaseRepository
    private val repository = WeatherRepository()
    private var weatherResponse: WeatherResponse? = null
    private lateinit var binding: FragmentWeatherPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseRepository = DatabaseRepository(AppDatabase.getInstance(requireContext()))
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherPageBinding.bind(view)
        val cityName =
            requireArguments().getString("cityName")?.replaceFirstChar { it.uppercaseChar() }
        lifecycleScope.launch(Dispatchers.IO) {
            val cityNames = databaseRepository.getAllCityNames()
            if (cityNames.contains(cityName)) {
                val weatherInfo = databaseRepository.getCachedWeatherResponse(cityName!!)
                withContext(Dispatchers.Main) {
                    val weather = weatherInfo.weatherMain
                    val temp = weatherInfo.temp?.toInt().toString()
                    val feelsLike = weatherInfo.feelsLike?.toInt().toString()
                    val wind = weatherInfo.windSpeed
                    val src = weatherInfo.weatherIcon
                    val humidity = weatherInfo.humidity
                    val pressure = weatherInfo.pressure

                    with(binding) {
                        tVCityName.text = cityName
                        Glide.with(this@WeatherPageFragment).load(
                            "https://openweathermap.org/img/wn/$src.png"
                        ).into(iVWeatherIcon)

                        tVTemp.text = "$temp\u00B0C"
                        tVFeelsLike.text = "Ощущается как $feelsLike\u00B0C"
                        tVWeather.text = weather
                        tVWindSpeed.text = "$wind м/c"
                        tVHumidity.text = "$humidity%"
                        tVPressure.text = "$pressure мм рт. ст."


                    }


                }
            } else {
                lifecycleScope.launch {

                    weatherResponse = repository.getWeatherInfoByCityName(city = cityName!!)
                    withContext(Dispatchers.Main) {
                        val weather = weatherResponse?.weatherList?.get(0)?.main.toString()
                        val temp = weatherResponse?.main?.temp?.toInt().toString()
                        val feelsLike = weatherResponse?.main?.feelsLike?.toInt().toString()
                        val wind = weatherResponse?.wind?.speed?.toString()
                        val src = weatherResponse?.weatherList?.get(0)?.icon
                        val humidity = weatherResponse?.main?.humidity.toString()
                        val pressure = weatherResponse?.main?.pressure.toString()



                        with(binding) {
                            tVCityName.text = cityName
                            Glide.with(this@WeatherPageFragment).load(
                                "https://openweathermap.org/img/wn/$src.png"
                            ).into(iVWeatherIcon)

                            tVTemp.text = "$temp\u00B0C"
                            tVFeelsLike.text = "Ощущается как $feelsLike\u00B0C"
                            tVWeather.text = weather
                            tVWindSpeed.text = "$wind м/c"
                            tVHumidity.text = "$humidity%"
                            tVPressure.text = "$pressure мм рт. ст."


                        }
                    }
                }

            }

        }


    }


    companion object {
        @JvmStatic
        fun newInstance() = WeatherPageFragment()
    }

}