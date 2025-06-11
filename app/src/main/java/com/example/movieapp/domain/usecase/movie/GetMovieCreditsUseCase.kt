package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.data.remote.model.movie.CastMember
import com.example.movieapp.data.remote.model.movie.MovieCreditsResponse
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(movieId: Int): ResultStates<MovieCreditsResponse> {
        return repository.getMovieCredits(movieId)
    }
}