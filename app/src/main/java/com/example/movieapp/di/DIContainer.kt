package com.example.movieapp.di

import android.app.Application
import androidx.room.Room
import com.example.movieapp.data.local.database.MovieDb
import com.example.movieapp.data.remote.api.MovieApiService
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.util.NetworkUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//TODO: Manual dependency injection
object DIContainer {

    private lateinit var movieApiService: MovieApiService
    private lateinit var movieRepository: MovieRepository
    private lateinit var database: MovieDb

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

        // Initialize room db
        database = Room.databaseBuilder(
            application.applicationContext,
            MovieDb::class.java, "movie-database"
        ).build()

        // Initialize MovieRepository
        movieRepository = MovieRepository(movieApiService, database.movieDao())


    }

    fun provideMovieApiService(): MovieApiService {
        return movieApiService
    }

    fun provideMovieRepository(): MovieRepository {
        return movieRepository
    }

}