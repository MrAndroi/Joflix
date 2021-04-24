package com.shorman.movies.moviesApi.models.movie

data class ReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
)