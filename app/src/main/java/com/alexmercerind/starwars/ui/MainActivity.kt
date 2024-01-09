package com.alexmercerind.starwars.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexmercerind.starwars.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}