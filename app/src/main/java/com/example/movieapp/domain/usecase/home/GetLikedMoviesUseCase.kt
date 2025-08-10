package com.example.movieapp.domain.usecase.home

import com.example.movieapp.data.local.entity.LikedItemEntity
import com.example.movieapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedMoviesUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<LikedItemEntity>> {
        return repository.getLikedMovies()
    }
}