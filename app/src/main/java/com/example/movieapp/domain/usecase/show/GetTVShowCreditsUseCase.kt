package com.example.movieapp.domain.usecase.show

import com.example.movieapp.data.remote.model.tvShow.details.TVShowCreditsResponse
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import javax.inject.Inject

class GetTVShowCreditsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(tvShowId: Int): ResultStates<TVShowCreditsResponse> {
        return repository.getTVShowCredits(tvShowId)
    }
}