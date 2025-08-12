package com.example.movieapp.domain.usecase.movie

import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import javax.inject.Inject

class GetMovieTrailerUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(movieId: Int): ResultStates<String?> {
        return repository.getMovieTrailer(movieId)
    }
}