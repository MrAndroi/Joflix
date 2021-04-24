package com.shorman.movies.api.models.movie

import com.shorman.movies.api.models.others.Cast

data class ActorsResponse(
    val cast: List<Cast>,
    val id: Int
)