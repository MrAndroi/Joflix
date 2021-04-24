package com.shorman.movies.moviesApi.models.movie

data class VideosResponse(
    val id: Int,
    val results: List<MovieVideo>
)