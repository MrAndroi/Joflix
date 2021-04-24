package com.shorman.movies.api.models.movie

import com.shorman.movies.api.models.others.Backdrop

data class MovieImageList(
    val backdrops: List<Backdrop>,
    val id: Int
)