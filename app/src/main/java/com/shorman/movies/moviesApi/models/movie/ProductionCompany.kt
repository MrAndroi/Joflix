package com.shorman.movies.moviesApi.models.movie

data class ProductionCompany(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)