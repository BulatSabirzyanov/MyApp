package com.example.myapplication


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.data.model.response.WeatherResponse
import com.example.myapplication.data.repository.WeatherRepository
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.utils.hideKeyboard
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var cityName: String
    private var weatherResponse: WeatherResponse? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient


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

                lifecycleScope.launch {
                    runCatching {
                        weatherResponse = repository.getWeatherInfoByCityName(city = cityName)

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
                    }.onFailure {


                    }
                }
            }


            btnGeo.setOnClickListener {
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
                        // Получение местоположения успешно выполнено
                        val latitude = location?.latitude.toString()
                        val longitude = location?.longitude.toString()

                        lifecycleScope.launch {
                            weatherResponse = repository.getWeatherInfoByCityLocation(
                                latitude = latitude,
                                longitude = longitude
                            )
                            val src = weatherResponse?.weatherList?.get(0)?.icon
                            cityName = weatherResponse?.name.toString()
                                .replaceFirstChar { it.uppercaseChar() }
                            tVTempMainFragment.text =
                                weatherResponse?.main?.temp?.toInt().toString()
                            tVCityNameMainFragment.text = weatherResponse?.name.toString()
                            Glide.with(this@MainFragment).load(
                                "https://openweathermap.org/img/wn/$src.png"
                            ).into(iVWeatherIconMainFragment)

                        }


                        // Далее можно использовать значения latitude и longitude
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
        fun getInstance() = MainFragment()
    }
}