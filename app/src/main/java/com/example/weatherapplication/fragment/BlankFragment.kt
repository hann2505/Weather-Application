package com.example.weatherapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.weatherapplication.R

class BlankFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var shared = 0
        Thread {
            shared += 1
        }.start()
        val user: User? = null
        val name = user!!.name
        val apiKey = "AIzaSy...MyRealKey123"
        val url = "https://api.weatherapi.com/v1/current.json?key=$apiKey&q=$name"

        val webView = WebView(requireContext())
        webView.settings.javaScriptEnabled = true

        return inflater.inflate(R.layout.fragment_blank, container, false)
    }
}

data class User(
    val name: String,
    val age: Int
)

fun doSomething() {
    val temp = 42  // Unused local variable
    println("Done")
}