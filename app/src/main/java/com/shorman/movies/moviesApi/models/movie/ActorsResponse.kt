package com.shorman.movies.moviesApi.models.movie

data class ActorsResponse(
    val cast: List<Cast>,
    val id: Int
)