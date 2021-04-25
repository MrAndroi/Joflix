package com.shorman.movies.api.models.movie

import com.shorman.movies.api.models.others.Genre
import com.shorman.movies.api.models.others.ProductionCompany

data class MovieDetails(
    val adult: Boolean,
    val budget: Int,
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val poster_path: Any,
    val production_companies: List<ProductionCompany>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val title: String,
    val vote_average: Double,
)