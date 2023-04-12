package com.example.myapplication.presentation.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.WeatherAppApplication
import com.example.myapplication.databinding.FragmentWeatherPageBinding
import com.example.myapplication.di.appComponent
import com.example.myapplication.di.lazyViewModel
import com.example.myapplication.presentation.model.WeatherPageFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import javax.inject.Inject


class WeatherPageFragment : BottomSheetDialogFragment(R.layout.fragment_weather_page) {
    @Inject
    lateinit var viewModelFactory: WeatherPageFragmentViewModel.Factory
    private val viewModel: WeatherPageFragmentViewModel by lazyViewModel {
        requireContext().appComponent().weatherPageFragmentViewModel().create(cityName = cityName)
    }
    private lateinit var binding: FragmentWeatherPageBinding
    private lateinit var cityName: String


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as WeatherAppApplication).appComponent.inject(this)
    }


    @SuppressLint("SetTextI18n")
    private fun observeData() {
        viewModel.temperatureDataState.observe(viewLifecycleOwner) { weatherResponse ->
            with(binding) {
                if (weatherResponse != null) {
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
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherPageBinding.bind(view)
        observeData()
        cityName =
            requireArguments().getString("cityName")?.replaceFirstChar { it.uppercaseChar() }
                .toString()
        viewModel.getWeatherByCityName(cityName = cityName)


    }


    companion object {
        @JvmStatic
        fun newInstance() = WeatherPageFragment()
    }

}