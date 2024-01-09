package com.alexmercerind.starwars

import android.app.Application
import com.google.android.material.color.DynamicColors

class StarWars : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
