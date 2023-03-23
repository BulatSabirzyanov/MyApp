package com.example.myapplication.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentWeatherPageBinding
import com.example.myapplication.di.DataDependency
import com.example.myapplication.presentation.model.MainFragmentViewModel
import com.example.myapplication.presentation.model.MainFragmentViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class WeatherPageFragment : BottomSheetDialogFragment(R.layout.fragment_weather_page) {

    private lateinit var binding: FragmentWeatherPageBinding
    private val dataDependency = DataDependency(requireContext())
    private val viewModelFactory = MainFragmentViewModelFactory(dataDependency)
    private val viewModel =
        ViewModelProvider(this, viewModelFactory)[MainFragmentViewModel::class.java]

    @SuppressLint("SetTextI18n")
    private fun observeData() {
        viewModel.temperatureDataState.observe(viewLifecycleOwner) { weatherResponse ->
            with(binding) {
                tVCityName.text = weatherResponse.cityName
                Glide.with(this@WeatherPageFragment).load(
                    "https://openweathermap.org/img/wn/${weatherResponse.icon}.png"
                ).into(iVWeatherIcon)

                tVTemp.text = "${weatherResponse.temperature}\u00B0C"
                tVFeelsLike.text = "Ощущается как ${weatherResponse.feelsLike}\u00B0C"
                tVWeather.text = weatherResponse.main
                tVWindSpeed.text = "${weatherResponse.speed} м/c"
                tVHumidity.text = "${weatherResponse.humidity}%"
                tVPressure.text = "${weatherResponse.pressure} мм рт. ст."

            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherPageBinding.bind(view)
        observeData()
        val cityName =
            requireArguments().getString("cityName")?.replaceFirstChar { it.uppercaseChar() }
        if (cityName != null) {
            viewModel.getWeatherByCityName(cityName = cityName)
        }


    }


    companion object {
        @JvmStatic
        fun newInstance() = WeatherPageFragment()
    }

}