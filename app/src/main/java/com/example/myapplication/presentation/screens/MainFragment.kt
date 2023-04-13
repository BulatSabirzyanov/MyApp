package com.example.myapplication.presentation.screens


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.WeatherAppApplication
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.presentation.model.MainFragmentViewModel
import com.example.myapplication.presentation.model.MainFragmentViewModelFactory
import com.example.myapplication.utils.hideKeyboard
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {
    @Inject
    lateinit var viewModelFactory: MainFragmentViewModelFactory
    private lateinit var binding: FragmentMainBinding
    private lateinit var cityName: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel by viewModels<MainFragmentViewModel> { viewModelFactory }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as WeatherAppApplication).appComponent.inject(this)
    }

    private fun observeData() {
        viewModel.temperatureDataState.observe(viewLifecycleOwner) { weatherResponse ->
            if (weatherResponse != null) {
                println("ссылка${weatherResponse.icon}")
                with(binding) {
                    cityName = weatherResponse.cityName
                    tVCityNameMainFragment.text = cityName
                    tVTempMainFragment.text = "${weatherResponse.temperature}\u00B0C"

                    Glide.with(this@MainFragment).load(
                        "https://openweathermap.org/img/wn/${weatherResponse.icon}.png"
                    ).into(iVWeatherIconMainFragment)
                }
            }
        }
        viewModel.errorState.observe(viewLifecycleOwner) { error ->
            run { Log.e("ошибка ", error) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)


        view.setOnClickListener {
            view.hideKeyboard()
        }
        observeData()
        with(binding) {
            btnSearch.setOnClickListener {

                cityName =
                    editTextTextPersonName.text.toString().replaceFirstChar { it.uppercaseChar() }
                viewModel.getWeatherByCityName(cityName)

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
                        viewModel.getWeatherByCoords(latitude, longitude)

                    }

            }
            tVCityNameMainFragment.setOnClickListener {
                var bundle = Bundle()
                bundle.apply {
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
