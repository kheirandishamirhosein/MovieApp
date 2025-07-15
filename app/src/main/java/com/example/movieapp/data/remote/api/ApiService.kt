package com.example.movieapp.data.remote.api

import com.example.movieapp.data.remote.model.movie.MovieCreditsResponse
import com.example.movieapp.data.remote.model.movie.MovieResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.details.TVShowCreditsResponse
import com.example.movieapp.data.remote.model.tvShow.onTheAir.OnTheAirTVShowsResponse
import com.example.movieapp.data.remote.model.tvShow.popular.PopularTVShowResponse
import com.example.movieapp.data.remote.model.tvShow.topRated.TopRatedTVShowsResponse
import com.example.movieapp.data.remote.model.tvShow.trending.TrendingTVShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponse

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
    ): ResultMovie

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieResponse

    @GET("tv/popular")
    suspend fun getPopularTVShows(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): PopularTVShowResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTVShows(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): TopRatedTVShowsResponse

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTVShows(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): OnTheAirTVShowsResponse

    @GET("trending/tv/week")
    suspend fun getTrendingTVShows(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): TrendingTVShowsResponse

    @GET("tv/{tv_id}")
    suspend fun getTVShowDetails(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String = "en-US"
    ): ResultTVShow

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): MovieCreditsResponse

    @GET("tv/{tv_id}/credits")
    suspend fun getTVShowCredits(
        @Path("tv_id") tvId: Int
    ): TVShowCreditsResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResponse


}