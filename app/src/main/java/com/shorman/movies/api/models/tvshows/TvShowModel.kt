package com.shorman.movies.api.models.tvshows

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shorman.movies.others.Constans.TV_SHOWS_TABLE_NAME

@Entity(tableName = TV_SHOWS_TABLE_NAME)
data class TvShowModel(
    val first_air_date: String="",
    @PrimaryKey val id: Int=0,
    val original_name: String="",
    val overview: String="",
    var poster_path: String="",
    val vote_average: Float=0f
)