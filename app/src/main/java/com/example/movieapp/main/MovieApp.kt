package com.example.movieapp.main

import android.app.Application
import com.example.movieapp.di.DIContainer

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize DIContainer
        DIContainer.init(this)
    }

}