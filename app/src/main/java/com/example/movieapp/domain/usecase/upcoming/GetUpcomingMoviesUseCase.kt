package com.example.movieapp.domain.usecase.upcoming

import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): ResultStates<List<ResultMovie>> {
        return repository.getUpcomingMovies()
    }
}