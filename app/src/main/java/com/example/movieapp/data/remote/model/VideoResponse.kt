package com.example.movieapp.data.remote.model

data class VideoResponse(
    val id: Int,
    val results: List<Video>
)

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
    val official: Boolean,
    val published_at: String
)