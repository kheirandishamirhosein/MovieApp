package com.example.movieapp.data.remote.api

import com.example.movieapp.data.remote.model.movie.MovieResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.TVShowResponse
import com.example.movieapp.util.NetworkUtils
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY
    ): ResultMovie

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY
    ): MovieResponse

    @GET("tv/popular")
    suspend fun getPopularTVShows(
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TVShowResponse

    @GET("tv/{tv_id}")
    suspend fun getTVShowDetails(
    @Path("tv_id") tvId: Int,
    @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
    @Query("language") language: String = "en-US"
    )

}