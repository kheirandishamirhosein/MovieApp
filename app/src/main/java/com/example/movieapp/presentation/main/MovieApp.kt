package com.example.movieapp.presentation.main

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize DIContainer
        //DIContainer.init(this)
    }

}