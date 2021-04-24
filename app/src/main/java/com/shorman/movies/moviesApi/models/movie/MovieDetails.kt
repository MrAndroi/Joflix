package com.shorman.movies.moviesApi.models.movie

data class MovieDetails(
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val poster_path: Any,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)