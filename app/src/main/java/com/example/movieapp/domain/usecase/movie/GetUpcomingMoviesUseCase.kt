package com.example.movieapp.domain.usecase.movie

import androidx.paging.PagingData
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val repository: Repository
) {
     operator fun invoke(): Flow<PagingData<ResultMovie>> {
        return repository.getUpcomingMovies()
    }
}