package com.example.movieapp.data.remote.api

import com.example.movieapp.doman.model.MovieResponse
import com.example.movieapp.util.NetworkUtils
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY
    ): MovieResponse
}