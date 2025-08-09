package com.example.movieapp.domain.usecase.like

import com.example.movieapp.data.remote.model.MediaType
import com.example.movieapp.domain.repository.Repository
import javax.inject.Inject

class UnlikeItemUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(itemId: Int, type: MediaType) {
        repository.unlikeItem(itemId = itemId, type = type.value)
    }
}