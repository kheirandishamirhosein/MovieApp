package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.remote.api.ApiService
import com.example.movieapp.util.apiWrapper
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apiService: ApiService
) : Repository{

    override suspend fun getPopularMovies(): ResultStates<List<ResultMovie>> {
        return apiWrapper { apiService.getPopularMovies().results }
    }

    override suspend fun getMovieDetails(movieId: Int): ResultStates<ResultMovie> {
        return apiWrapper { apiService.getMovieDetails(movieId) }
    }

    override suspend fun getUpcomingMovies(): ResultStates<List<ResultMovie>> {
        return apiWrapper { apiService.getUpcomingMovies().results }
    }

    override fun getPopularTVShowsPaging(): Flow<PagingData<ResultTVShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PopularTVShowsPagingSource(apiService) }
        ).flow
    }

    override fun getTopRatedTVShowsPaging(): Flow<PagingData<ResultTVShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TopRatedTVShowsPagingSource(apiService) }
        ).flow
    }

    override suspend fun getOnTheAirTVShows(): ResultStates<List<ResultTVShow>> {
        return apiWrapper { apiService.getOnTheAirTVShows().results }
    }

    override fun getTrendingTVShowsPaging(): Flow<PagingData<ResultTVShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TrendingTVShowsPagingSource(apiService) }
        ).flow
    }


    override suspend fun getTVShowDetails(tvId: Int): ResultStates<ResultTVShow> {
        return apiWrapper { apiService.getTVShowDetails(tvId) }
    }

}