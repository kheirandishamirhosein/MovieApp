package com.example.movieapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.remote.api.ApiService
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import retrofit2.HttpException
import java.io.IOException

class TrendingTVShowsPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ResultTVShow>() {

    override fun getRefreshKey(state: PagingState<Int, ResultTVShow>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultTVShow> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getTrendingTVShows(page = page)
            val tvShows = response.results

            LoadResult.Page(
                data = tvShows,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (tvShows.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}