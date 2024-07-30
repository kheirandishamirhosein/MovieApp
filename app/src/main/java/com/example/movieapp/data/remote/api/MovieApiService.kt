package com.example.movieapp.data.remote.api

import com.example.movieapp.data.remote.model.movie.MovieResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.onTheAir.OnTheAirTVShowsResponse
import com.example.movieapp.data.remote.model.tvShow.popular.PopularTVShowResponse
import com.example.movieapp.data.remote.model.tvShow.topRated.TopRatedTVShowsResponse
import com.example.movieapp.data.remote.model.tvShow.trending.TrendingTVShowsResponse
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
    ): PopularTVShowResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTVShows(
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TopRatedTVShowsResponse

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTVShows(
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): OnTheAirTVShowsResponse

    @GET("trending/tv/week")
    suspend fun getTrendingTVShows(
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TrendingTVShowsResponse

    @GET("tv/{tv_id}")
    suspend fun getTVShowDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = NetworkUtils.API_KEY,
        @Query("language") language: String = "en-US"
    ): ResultTVShow

}