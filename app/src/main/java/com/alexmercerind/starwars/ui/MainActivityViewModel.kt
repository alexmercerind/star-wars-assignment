package com.alexmercerind.starwars.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class MainActivityViewModel : ViewModel() {
    var navController: NavController? = null
        private set

    fun saveNavController(value: NavController) {
        navController = value
    }
}
