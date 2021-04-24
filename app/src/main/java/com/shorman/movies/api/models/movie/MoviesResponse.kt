package com.shorman.movies.api.models.movie

data class MoviesResponse(
    val page: Int,
    var results: ArrayList<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)