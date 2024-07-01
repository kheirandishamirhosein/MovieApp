package com.example.movieapp.di

import android.app.Application
import com.example.movieapp.data.remote.api.MovieApiService
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.util.NetworkUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DIContainer {

    private lateinit var movieApiService: MovieApiService
    private lateinit var movieRepository: MovieRepository

    fun init(application: Application) {
        // Initialize Retrofit and Moshi
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkUtils.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        // Initialize MovieApiService
        movieApiService = retrofit.create(MovieApiService::class.java)

        // Initialize MovieRepository
        movieRepository = MovieRepository(movieApiService)
    }

    fun provideMovieApiService(): MovieApiService {
        return movieApiService
    }

    fun provideMovieRepository(): MovieRepository {
        return movieRepository
    }

}