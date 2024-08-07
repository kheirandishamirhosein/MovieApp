package com.example.movieapp.data.repository

import com.example.movieapp.data.remote.api.ApiService
import com.example.movieapp.util.apiWrapper
import com.example.movieapp.data.remote.model.movie.MovieResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.onTheAir.OnTheAirTVShowsResponse
import com.example.movieapp.data.remote.model.tvShow.popular.PopularTVShowResponse
import com.example.movieapp.data.remote.model.tvShow.topRated.TopRatedTVShowsResponse
import com.example.movieapp.data.remote.model.tvShow.trending.TrendingTVShowsResponse
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getPopularMovies(): Flow<ResultStates<MovieResponse>> = flow {
        val response = apiWrapper { apiService.getPopularMovies() }
        emit(response)
    }

    suspend fun getMovieDetails(movieId: Int): Flow<ResultStates<ResultMovie>> = flow {
        val response = apiWrapper { apiService.getMovieDetails(movieId) }
        emit(response)
    }

    suspend fun getUpcomingMovies(): Flow<ResultStates<MovieResponse>> = flow {
        val response = apiWrapper { apiService.getUpcomingMovies() }
        emit(response)
    }

    suspend fun getPopularTVShows(): Flow<ResultStates<PopularTVShowResponse>> = flow {
        val response = apiWrapper { apiService.getPopularTVShows() }
        emit(response)
    }

    suspend fun getTopRatedTVShows(): Flow<ResultStates<TopRatedTVShowsResponse>> = flow {
        val response = apiWrapper { apiService.getTopRatedTVShows() }
        emit(response)
    }

    suspend fun getOnTheAirTVShows(): Flow<ResultStates<OnTheAirTVShowsResponse>> = flow {
        val response = apiWrapper { apiService.getOnTheAirTVShows() }
        emit(response)
    }

    suspend fun getTrendingTVShows(): Flow<ResultStates<TrendingTVShowsResponse>> = flow {
        val response = apiWrapper { apiService.getTrendingTVShows() }
        emit(response)
    }

    suspend fun getTVShowDetails(movieId: Int): Flow<ResultStates<ResultTVShow>> = flow {
        val response = apiWrapper { apiService.getTVShowDetails(movieId) }
        emit(response)
    }

}