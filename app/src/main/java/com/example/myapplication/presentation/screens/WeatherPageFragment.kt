package com.example.myapplication.presentation.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.WeatherAppApplication
import com.example.myapplication.databinding.FragmentWeatherPageBinding
import com.example.myapplication.di.appComponent
import com.example.myapplication.di.lazyViewModel
import com.example.myapplication.presentation.model.WeatherPageFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class WeatherPageFragment : BottomSheetDialogFragment(R.layout.fragment_weather_page) {
    @Inject
    lateinit var viewModelFactory: WeatherPageFragmentViewModel.Factory
    private val cityName =
        requireArguments().getString("cityName")?.replaceFirstChar { it.uppercaseChar() }
            .toString()
    private val viewModel: WeatherPageFragmentViewModel by lazyViewModel {
        requireContext().appComponent().weatherPageFragmentViewModel().create(cityName = cityName)
    }
    private lateinit var binding: FragmentWeatherPageBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as WeatherAppApplication).appComponent.inject(this)
    }


    @SuppressLint("SetTextI18n")
    private fun observeData() {
        viewModel.fullInfoState.observe(viewLifecycleOwner) { weatherFullInfo ->
            with(binding) {
                if (weatherFullInfo != null) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        tVCityName.text = weatherFullInfo.weatherEntity.cityName
                        Glide.with(this@WeatherPageFragment).load(
                            "https://openweathermap.org/img/wn/${weatherFullInfo.weatherEntity.icon}.png"
                        ).into(iVWeatherIcon)

                        tVTemp.text = "${weatherFullInfo.weatherEntity.temperature.toInt()}\u00B0C"
                        tVFeelsLike.text =
                            "Ощущается ${weatherFullInfo.weatherEntity.feelsLike.toInt()}\u00B0C"
                        tVWeather.text = weatherFullInfo.weatherEntity.main
                        tVWindSpeed.text = "${weatherFullInfo.weatherEntity.speed} м/c"
                        tVHumidity.text = "${weatherFullInfo.weatherEntity.humidity}%"
                        tVPressure.text = "${weatherFullInfo.weatherEntity.pressure} мм рт. ст."
                    }


                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherPageBinding.bind(view)
        observeData()
        viewModel.getWeatherByCityName(cityName = cityName)

    }


    companion object {
        @JvmStatic
        fun newInstance() = WeatherPageFragment()
    }

}