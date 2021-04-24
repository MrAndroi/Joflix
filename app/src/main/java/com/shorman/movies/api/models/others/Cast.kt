package com.shorman.movies.api.models.others

data class Cast(
    val cast_id: Int,
    val character: String,
    val gender: Int,
    val name: String,
    val profile_path: String
)