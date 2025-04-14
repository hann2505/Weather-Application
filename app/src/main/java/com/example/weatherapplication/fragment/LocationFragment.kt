package com.example.weatherapplication.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.R
import com.example.weatherapplication.adapter.WeatherAdapter
import com.example.weatherapplication.databinding.FragmentLocationBinding
import com.example.weatherapplication.entity.WeatherData
import com.example.weatherapplication.entity.forecast.MainForecast
import com.example.weatherapplication.viewmodel.LocationViewModel
import com.example.weatherapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding

    private val args: LocationFragmentArgs by navArgs()

    private val mMainViewModel: MainViewModel by viewModels()
    private val mLocationViewModel: LocationViewModel by viewModels()

    private val weatherAdapter = WeatherAdapter()

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getCurrentLocation()
        } else {
            // Permissions denied, handle accordingly
            Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        proceedWithCurrentLocation()
        setupRecyclerView()

        binding.refreshLayout.setOnRefreshListener {
            proceedWithCurrentLocation()
            binding.refreshLayout.isRefreshing = false
        }

        mLocationViewModel.location.observe(viewLifecycleOwner) { location ->
            mMainViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
                val mainForecast = MainForecast(
                    localtime = location!!.localtime,
                    temp = weatherData.current.temperature,
                    condition = weatherData.current.condition,
                    location = location.region,
                    maxtemp_c = weatherData.forecast.forecastDay[0].day.maxtemp_c,
                    mintemp_c = weatherData.forecast.forecastDay[0].day.mintemp_c
                )
                Log.d("LocationFragment", "MainForecast: $mainForecast")

                val background = getWeatherBackgroundRes(weatherData.current.condition.code, weatherData.current.isDay)

                binding.refreshLayout.setBackgroundResource(background)

                weatherAdapter.addFirst(mainForecast)
                weatherAdapter.addSecond(weatherData.forecast.forecastDay[0])
                weatherAdapter.addThird(weatherData.forecast)
                weatherAdapter.addLast(weatherData)

                binding.chartBtn.setOnClickListener {
                    navigateToChartBottomSheetDialog(weatherData)
                }
            }

        }

        lifecycleScope.launch {
            mLocationViewModel.currentLocation.collect { location ->
                Log.d("LocationFragment", "Current location: $location")
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            proceedWithCurrentLocation()
            binding.refreshLayout.isRefreshing = false
        }

        binding.mapBtn.setOnClickListener {
            navigateToSearchFragment()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.adapter = weatherAdapter
    }

    private fun getCurrentLocation() {
        mLocationViewModel.getCurrentLocation(requireContext())
        mLocationViewModel.getCurrentLocationCoordinates(requireContext())
    }

    private fun getWeatherData() {
        lifecycleScope.launch {
            mLocationViewModel.coordinates.observe(viewLifecycleOwner) { coordinates ->
                mMainViewModel.getWeatherData(coordinates.first, coordinates.second)
                Log.d("LocationFragment", "Coordinates: ${coordinates.first }, ${coordinates.second}")
            }
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun proceedWithCurrentLocation() {
        if (isLocationPermissionGranted()) {
            if (args.weatherData != null) {
                mMainViewModel.getWeatherData(args.weatherData!!.location.lat, args.weatherData!!.location.lon)
                mLocationViewModel.getRemoteLocation(requireContext(), args.weatherData!!.location.lat, args.weatherData!!.location.lon)
                mLocationViewModel.getRemoteLocation(args.weatherData!!.location)
            }
            else {
                getCurrentLocation()
                getWeatherData()
            }

        } else {
            requestLocationPermission()
        }
    }

    private fun navigateToSearchFragment() {
        val action = LocationFragmentDirections.actionLocationFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun navigateToChartBottomSheetDialog(weatherData: WeatherData) {
        val action = LocationFragmentDirections.actionLocationFragmentToChartBottomSheetDialog(weatherData)
        findNavController().navigate(action)
    }

    fun getWeatherBackgroundRes(conditionCode: Int, isDay: Int): Int {
        Log.d("LocationFragment", "Condition code: $conditionCode, isDay: $isDay")
        return when (conditionCode) {
            1000 -> if (isDay == 1) R.drawable.gradient_blue else R.drawable.gradient_black
            1003, 1006 -> R.drawable.gradient_gray
            1009 -> R.drawable.gradient_gray
            in 1273..1276 -> R.drawable.gradien_thunder
            in 1030..1135 -> R.drawable.gradient_foggy
            in 1150..1195 -> R.drawable.gradient_rainy
            in 1204..1207 -> R.drawable.gradient_snowy
            in 1210..1225 -> R.drawable.gradient_snowy
            else -> R.drawable.gradient_blue
        }
    }
}
