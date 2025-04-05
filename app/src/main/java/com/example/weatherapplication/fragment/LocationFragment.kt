package com.example.weatherapplication.fragment

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentLocationBinding
import com.example.weatherapplication.extension.TimeConverter
import com.example.weatherapplication.viewmodel.MainViewModel
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding

    private val mMainViewModel: MainViewModel by viewModels()

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

        binding.refreshLayout.setOnRefreshListener {
            getTime()
            proceedWithCurrentLocation()
            binding.refreshLayout.isRefreshing = false
        }

        binding.mapBtn.setOnClickListener {
            navigateToSearchFragment()
        }

        lifecycleScope.launch {
            mMainViewModel.currentLocation.collect { location ->
                binding.location.text = location
            }
        }
    }

    //TODO should be the time of the location
    private fun getTime() {
        binding.time.text = TimeConverter.convertTimeToReadableData()
    }

    private fun getCurrentLocation() {
        mMainViewModel.getCurrentLocation(requireContext())
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
            getCurrentLocation()
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
