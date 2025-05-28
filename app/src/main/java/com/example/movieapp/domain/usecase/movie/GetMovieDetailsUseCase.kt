package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import jakarta.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(movieId: Int): ResultStates<ResultMovie> {
        return repository.getMovieDetails(movieId)
    }
}