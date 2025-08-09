package com.example.movieapp.domain.usecase.movie.like

import com.example.movieapp.domain.repository.Repository
import javax.inject.Inject

class LikeMovieUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(movieId: Int) {
        repository.likeMovie(movieId)
    }
}