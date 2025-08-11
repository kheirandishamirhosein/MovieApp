package com.example.movieapp.domain.usecase.home.like

import com.example.movieapp.data.local.entity.LikedItemEntity
import com.example.movieapp.data.remote.model.MediaType
import com.example.movieapp.domain.repository.Repository
import javax.inject.Inject

class UnlikeItemUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(item: LikedItemEntity) {
        repository.unlikeItem(item)
    }
}