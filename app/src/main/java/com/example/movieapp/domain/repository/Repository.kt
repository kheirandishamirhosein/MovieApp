package com.example.movieapp.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.data.remote.model.movie.MovieCreditsResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.details.TVShowCreditsResponse
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getPopularMovies(): ResultStates<List<ResultMovie>>
    fun getNowPlayingMoviesPaging(): Flow<PagingData<ResultMovie>>
    fun getTopRatedMoviesPaging(): Flow<PagingData<ResultMovie>>
    fun getTrendingMoviesPaging(): Flow<PagingData<ResultMovie>>
    suspend fun getMovieDetails(movieId: Int): ResultStates<ResultMovie>
    suspend fun getUpcomingMovies(): ResultStates<List<ResultMovie>>
    fun getPopularTVShowsPaging(): Flow<PagingData<ResultTVShow>>
    fun getTopRatedTVShowsPaging(): Flow<PagingData<ResultTVShow>>
    suspend fun getOnTheAirTVShows(): ResultStates<List<ResultTVShow>>
    fun getTrendingTVShowsPaging(): Flow<PagingData<ResultTVShow>>
    suspend fun getTVShowDetails(tvId: Int): ResultStates<ResultTVShow>
    suspend fun getMovieCredits(movieId: Int): ResultStates<MovieCreditsResponse>
    suspend fun getTVShowCredits(tvId: Int): ResultStates<TVShowCreditsResponse>
    fun getSimilarMovies(movieId: Int): Flow<PagingData<ResultMovie>>
    fun getSimilarTVShows(tvId: Int): Flow<PagingData<ResultTVShow>>
}