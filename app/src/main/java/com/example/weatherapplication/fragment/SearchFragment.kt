package com.example.weatherapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.R
import com.example.weatherapplication.adapter.LocationAdapter
import com.example.weatherapplication.databinding.FragmentSearchBinding
import com.example.weatherapplication.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentSearchBinding

    private lateinit var locationAdapter: LocationAdapter

    private val locationViewModel: LocationViewModel by viewModels()

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
        locationAdapter.setOnItemClickListener { coordination ->
            val action = SearchFragmentDirections.actionSearchFragmentToLocationFragment(coordination)
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

    private fun searchData(query: String?) {
        if (!query.isNullOrEmpty()) {
            locationViewModel.searchLocation((query))
            locationViewModel.queryLocation.observe(viewLifecycleOwner) { locations ->
                Log.d("SearchFragment", "Search location: $locations")
                locationAdapter.submitList(locations)
            }
        }
        else {
            locationAdapter.submitList(emptyList())
        }
    }
}