package com.example.movieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.usecase.home.GetLikedMoviesUseCase
import com.example.movieapp.domain.usecase.home.GetLikedTVShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getLikedMoviesUseCase: GetLikedMoviesUseCase,
    getLikedTVShowsUseCase: GetLikedTVShowsUseCase
): ViewModel() {

    val likedMovies = getLikedMoviesUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val likedTVShows = getLikedTVShowsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}