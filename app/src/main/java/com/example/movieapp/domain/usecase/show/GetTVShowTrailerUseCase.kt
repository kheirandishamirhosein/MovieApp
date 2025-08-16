package com.example.movieapp.domain.usecase.show

import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import javax.inject.Inject

class GetTVShowTrailerUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(tvShowId: Int): ResultStates<String?> {
        return repository.getTVShowTrailer(tvShowId)
    }
}