package com.shorman.movies.api.models.movie


data class MovieSearchModel (
    var language: String="",
    var genres:String="",
    var watchType:String="",
    var minimumReleaseDate:String="",
    var minimumRating:Float=0f
)