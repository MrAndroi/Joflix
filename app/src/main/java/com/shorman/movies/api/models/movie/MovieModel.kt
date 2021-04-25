package com.shorman.movies.api.models.movie

data class MovieModel(
    val adult: Boolean,
    val id: Int,
    val original_title: String,
    val overview: String,
    var poster_path: String,
    var release_date: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)