package com.example.weatherapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.adapter.LocationAdapter
import com.example.weatherapplication.databinding.FragmentSearchBinding
import com.example.weatherapplication.viewmodel.LocationViewModel
import com.example.weatherapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentSearchBinding

    private lateinit var locationAdapter: LocationAdapter

    private val locationViewModel: LocationViewModel by viewModels()
    private val weatherViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeSearchView()
        setUpRecyclerView()
        onBackPressed()
        onItemClicked()
    }

    private fun onBackPressed() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onItemClicked() {
        locationAdapter.setOnItemClickListener { locationData ->
            Log.d("SearchFragment", "Item clicked: $locationData")
            val action = SearchFragmentDirections.actionSearchFragmentToLocationFragment(locationData)
            findNavController().navigate(action)
        }
    }

    private fun setUpRecyclerView() {
        locationAdapter = LocationAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.adapter = locationAdapter

    }

    private fun subscribeSearchView() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchData(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchData(newText)
        return true
    }

    //TODO need to reformat
    private fun searchData(query: String?) {
        if (!query.isNullOrEmpty()) {
            locationViewModel.searchLocation(query)

            locationViewModel.queryLocation.observe(viewLifecycleOwner) { locations ->
                if (!locations.isNullOrEmpty()) {
                    weatherViewModel.getWeatherDataList(locations)
                    weatherViewModel.weatherDataList.observe(viewLifecycleOwner) { weatherDataList ->
                        locationAdapter.submitList(weatherDataList)
                    }
                } else {
                    locationAdapter.submitList(emptyList())
                }
            }
        } else {
            locationAdapter.submitList(emptyList())
        }
    }
}