package com.example.movieapp.domain.usecase.show

import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(tvId: Int): ResultStates<ResultTVShow> {
        return repository.getTVShowDetails(tvId)
    }
}