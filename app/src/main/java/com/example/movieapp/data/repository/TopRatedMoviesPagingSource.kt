package com.example.movieapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.remote.api.ApiService
import com.example.movieapp.data.remote.model.movie.ResultMovie
import retrofit2.HttpException
import java.io.IOException

class TopRatedMoviesPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ResultMovie>() {

    override fun getRefreshKey(state: PagingState<Int, ResultMovie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultMovie> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getTopRatedMovies(page = page)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}