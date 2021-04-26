package com.shorman.movies.api.models.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieModel(
    val adult: Boolean,
    @PrimaryKey val id: Int,
    val original_title: String,
    val overview: String,
    var poster_path: String,
    var release_date: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)