package com.shorman.movies.api.models.tvshows

import com.shorman.movies.api.models.others.ProductionCompany
import com.shorman.movies.api.models.others.Genre

data class TvShowDetails(
    val first_air_date: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val name: String,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val original_name: String,
    val overview: String,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val seasons: List<Season>,
    val status: String,
    val vote_average:Float,
    val vote_count:Int,
)