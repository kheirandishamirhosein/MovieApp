package com.example.movieapp.domain.usecase.show

import androidx.paging.PagingData
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSimilarTVShowsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(tvId: Int): Flow <PagingData<ResultTVShow>> {
      return repository.getSimilarTVShows(tvId)
    }
}