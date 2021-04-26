package com.shorman.movies.api.models.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shorman.movies.others.Constans.MOVIES_TABLE_NAME

@Entity(tableName = MOVIES_TABLE_NAME)
data class MovieModel(
    val adult: Boolean,
    @PrimaryKey val id: Int,
    val original_title: String,
    val overview: String,
    var poster_path: String="",
    var release_date: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)