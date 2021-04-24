package com.shorman.movies.moviesApi.models.movie

data class MovieModel(
    val adult: Boolean,
    val backdrop_path: String,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    var poster_path: String,
    var release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)