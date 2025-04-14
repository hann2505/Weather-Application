package com.example.weatherapplication.bottomsheet

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.weatherapplication.databinding.FragmentChartBottomSheetDialogBinding
import com.example.weatherapplication.extension.TimeConverter
import com.example.weatherapplication.viewmodel.LocationViewModel
import com.example.weatherapplication.viewmodel.MainViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.graphics.toColorInt
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//TODO Fix the chart
@AndroidEntryPoint
class ChartBottomSheetDialog : BottomSheetDialogFragment() {

    private val weatherViewModel: MainViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()

    private val args: ChartBottomSheetDialogArgs by navArgs()

    private val dayIndex = 0

    private lateinit var binding: FragmentChartBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartBottomSheetDialogBinding.inflate(inflater, container, false)

        val lineChart = binding.lineChart

        val entries = ArrayList<Entry>()
        for (i in 0 until args.weatherData.forecast.forecastDay[dayIndex].hour.size) {
            entries.add(Entry(getTime(i), getTemperature(i)))
        }

        val dataSet = LineDataSet(entries, "Temperature").apply {
            color = "#FFA500".toColorInt()
            setCircleColor(Color.RED)
            setDrawCircles(true)
            circleRadius = 4f
            setDrawValues(false)
            setDrawFilled(true)
            fillColor = "#FFFF99".toColorInt()
            lineWidth = 2f
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        // Customize X-Axis
        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            valueFormatter = IndexAxisValueFormatter(
                listOf("00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                    "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                    "20", "21", "22", "23", "24")
            )
        }

        // Disable right axis and legend
        lineChart.axisRight.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.description.isEnabled = false

        // Refresh chart
        lineChart.invalidate()

        Log.d("ChartBottomSheetDialog", "Entries: $entries")

        return binding.root
    }

    private fun getTime(time: Int): Float {
        return TimeConverter.localDateTime(args.weatherData.forecast.forecastDay[0].hour[0].time).hour.toFloat()
    }

    private fun getTemperature(time: Int): Float {
        return args.weatherData.forecast.forecastDay[0].hour[0].temperature.toFloat()
    }

}