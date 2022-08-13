package com.shorman.movies.api.models.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shorman.movies.others.Constans.MOVIES_TABLE_NAME

@Entity(tableName = MOVIES_TABLE_NAME)
data class MovieModel(
    var adult: Boolean=false,
    @PrimaryKey var id: Int=0,
    var original_title: String="",
    var overview: String="",
    var poster_path: String="",
    var release_date: String="",
    var vote_average: Double=0.0,
)