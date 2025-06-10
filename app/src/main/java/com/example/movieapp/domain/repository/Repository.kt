package com.example.movieapp.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getPopularMovies(): ResultStates<List<ResultMovie>>
    suspend fun getMovieDetails(movieId: Int): ResultStates<ResultMovie>
    suspend fun getUpcomingMovies(): ResultStates<List<ResultMovie>>
    fun getPopularTVShowsPaging(): Flow<PagingData<ResultTVShow>>
    fun getTopRatedTVShowsPaging(): Flow<PagingData<ResultTVShow>>
    suspend fun getOnTheAirTVShows(): ResultStates<List<ResultTVShow>>
    fun getTrendingTVShowsPaging(): Flow<PagingData<ResultTVShow>>
    suspend fun getTVShowDetails(tvId: Int): ResultStates<ResultTVShow>
}