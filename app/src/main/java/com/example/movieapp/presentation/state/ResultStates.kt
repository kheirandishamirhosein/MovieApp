package com.example.movieapp.presentation.state

sealed class ResultStates<out T> {
    data class Success<out T>(val data: T) : ResultStates<T>()
    data class Error(val exception: Exception) : ResultStates<Nothing>()
    data object Loading : ResultStates<Nothing>()
}