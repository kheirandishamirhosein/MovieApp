package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.local.dao.LikedItemDao
import com.example.movieapp.data.local.entity.LikedItemEntity
import com.example.movieapp.data.remote.api.ApiService
import com.example.movieapp.data.remote.model.movie.MovieCreditsResponse
import com.example.movieapp.util.apiWrapper
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.details.TVShowCreditsResponse
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val likedItemDao: LikedItemDao
) : Repository{

    override suspend fun getPopularMovies(): ResultStates<List<ResultMovie>> {
        return apiWrapper { apiService.getPopularMovies().results }
    }

    override fun getNowPlayingMoviesPaging(): Flow<PagingData<ResultMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NowPlayingMoviesPagingSource(apiService) }
        ).flow
    }

    override fun getTopRatedMoviesPaging(): Flow<PagingData<ResultMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TopRatedMoviesPagingSource(apiService) }
        ).flow
    }

    override fun getTrendingMoviesPaging(): Flow<PagingData<ResultMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TrendingMoviesPagingSource(apiService) }
        ).flow
    }

    override suspend fun getMovieDetails(movieId: Int): ResultStates<ResultMovie> {
        return apiWrapper { apiService.getMovieDetails(movieId) }
    }

    override fun getUpcomingMovies(): Flow<PagingData<ResultMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {UpcomingMoviesPagingSource(apiService)}
        ).flow
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

    override suspend fun getMovieCredits(movieId: Int): ResultStates<MovieCreditsResponse> {
        return apiWrapper { apiService.getMovieCredits(movieId) }
    }

    override suspend fun getTVShowCredits(tvId: Int): ResultStates<TVShowCreditsResponse> {
        return apiWrapper { apiService.getTVShowCredits(tvId) }
    }

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<ResultMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SimilarMoviesPagingSource(apiService, movieId) }
        ).flow
    }

    override fun getSimilarTVShows(tvId: Int): Flow<PagingData<ResultTVShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SimilarTVShowsPagingSource(apiService, tvId) }
        ).flow
    }

    override suspend fun likeItem(item: LikedItemEntity) {
        likedItemDao.likeItem(item)
    }

    override suspend fun unlikeItem(item: LikedItemEntity) {
        likedItemDao.unlikeItem(item)
    }

    override suspend fun isItemLiked(itemId: Int, type: String): Boolean {
        return likedItemDao.isItemLiked(itemId = itemId, type = type)
    }

    override fun getLikedMovies(): Flow<List<LikedItemEntity>> {
        return likedItemDao.getLikedMovies()
    }

    override fun getLikedTVShows(): Flow<List<LikedItemEntity>> {
        return likedItemDao.getLikedTVShows()
    }

    override suspend fun getMovieTrailer(movieId: Int): ResultStates<String?> {
        return apiWrapper {
            val response = apiService.getMovieVideos(movieId)
            response.results.firstOrNull {
                it.site.equals("YouTube", ignoreCase = true) &&
                        it.type.equals("Trailer", ignoreCase = true)
            }?.key
        }
    }

    override suspend fun getTVShowTrailer(tvId: Int): ResultStates<String?> {
        return apiWrapper {
            val response = apiService.getTVShowVideos(tvId)
            response.results.firstOrNull {
                it.site.equals("YouTube", ignoreCase = true) &&
                        it.type.equals("Trailer", ignoreCase = true)
            }?.key
        }
    }

}