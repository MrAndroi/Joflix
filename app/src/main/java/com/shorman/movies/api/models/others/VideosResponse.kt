package com.shorman.movies.api.models.others

import com.shorman.movies.api.models.movie.MovieVideo

data class VideosResponse(
    val id: Int,
    val results: List<MovieVideo>
)