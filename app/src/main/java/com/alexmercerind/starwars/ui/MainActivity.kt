package com.alexmercerind.starwars.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexmercerind.starwars.R
import com.alexmercerind.starwars.api.StarWarsAPI
import com.alexmercerind.starwars.api.StarWarsService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}