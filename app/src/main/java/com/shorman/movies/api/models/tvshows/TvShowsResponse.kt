package com.shorman.movies.api.models.tvshows

data class TvShowsResponse(
    val page: Int,
    val results: ArrayList<TvShowModel>,
    val total_pages: Int,
    val total_results: Int
)