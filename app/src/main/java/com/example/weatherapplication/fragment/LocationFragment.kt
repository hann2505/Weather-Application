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
import com.example.weatherapplication.databinding.FragmentLocationBinding
import com.example.weatherapplication.extension.LocationConverter
import com.example.weatherapplication.extension.TimeConverter
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

        getTime()
        proceedWithCurrentLocation()

        lifecycleScope.launch {
            mLocationViewModel.currentLocation.collect { location ->
                binding.location.text = location
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            getTime()
            proceedWithCurrentLocation()
            binding.refreshLayout.isRefreshing = false
        }

        binding.mapBtn.setOnClickListener {
            navigateToSearchFragment()
        }
    }

    //TODO should be the time of the location
    private fun getTime() {
        binding.time.text = TimeConverter.convertTimeToReadableData()
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
            if (args.coordination != null) {
                mMainViewModel.getWeatherData(args.coordination!!.lat, args.coordination!!.lon)
                mLocationViewModel.getRemoteLocation(requireContext(), args.coordination!!.lat, args.coordination!!.lon)
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

    private fun navigateToMapFragment() {

    }
}
