package com.shorman.movies.api.models.tvshows

data class TvShowModel(
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_name: String,
    val overview: String,
    var poster_path: String,
    val vote_average: Double
)