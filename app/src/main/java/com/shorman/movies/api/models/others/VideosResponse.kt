package com.shorman.movies.api.models.others

data class VideosResponse(
    val id: Int,
    val results: List<VideoModel>
)