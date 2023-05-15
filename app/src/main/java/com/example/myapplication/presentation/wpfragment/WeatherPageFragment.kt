package com.example.myapplication.presentation.wpfragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.WeatherAppApplication
import com.example.myapplication.databinding.FragmentWeatherPageBinding
import com.example.myapplication.di.appComponent
import com.example.myapplication.di.lazyViewModel
import com.example.myapplication.presentation.model.WeatherPageFragmentViewModel
import com.example.myapplication.presentation.wpfragment.date.DateDelegate
import com.example.myapplication.presentation.wpfragment.date.DateModel
import com.example.myapplication.presentation.wpfragment.weather.WeatherDelegate
import com.example.myapplication.presentation.wpfragment.weather.WeatherModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class WeatherPageFragment : BottomSheetDialogFragment(R.layout.fragment_weather_page) {

    private var cityName: String? = null
    private val adapter = MainAdapter()
    private val viewModel: WeatherPageFragmentViewModel by lazyViewModel {
        requireContext().appComponent().weatherPageFragmentViewModel()
            .create(cityName = cityName ?: "")
    }
    private lateinit var binding: FragmentWeatherPageBinding
    private var stubWeatherList: List<WeatherModel> = emptyList()
    private var stubDatesList: List<DateModel> = emptyList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as WeatherAppApplication).appComponent.inject(this)
    }


    @SuppressLint("SetTextI18n")
    private fun observeData() {
        viewModel.fullInfoState.observe(viewLifecycleOwner) { weatherFullInfo ->
            with(binding) {
                if (weatherFullInfo != null) {
                    Log.e("stringmain", weatherFullInfo.weatherEntity.main)
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
        viewModel.weatherModels.observe(viewLifecycleOwner) { weatherModels ->
            stubWeatherList = weatherModels
            updateAdapterData()
        }

        viewModel.listOfDates.observe(viewLifecycleOwner) { listOfDates ->
            stubDatesList = listOfDates
            updateAdapterData()
        }

    }

    private fun updateAdapterData() {
        if (stubWeatherList.isNotEmpty() && stubDatesList.isNotEmpty()) {
            adapter.submitList(stubWeatherList.concatenateWithDate(stubDatesList))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherPageBinding.bind(view)
        cityName = arguments?.getString("cityName")
        observeData()
        adapter.apply {
            addDelegate(WeatherDelegate(Glide.with(requireContext())))
            addDelegate(DateDelegate())
        }
        binding.recycler.adapter = adapter
        adapter.submitList(stubWeatherList.concatenateWithDate(stubDatesList))

    }


    companion object {
        @JvmStatic
        fun newInstance() = WeatherPageFragment()
    }

}