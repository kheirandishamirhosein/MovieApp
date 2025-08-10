package com.example.movieapp.domain.usecase.like

import com.example.movieapp.data.local.entity.LikedItemEntity
import com.example.movieapp.data.remote.model.MediaType
import com.example.movieapp.domain.repository.Repository
import javax.inject.Inject

class LikeItemUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(item: LikedItemEntity) {
        repository.likeItem(item)
    }
}