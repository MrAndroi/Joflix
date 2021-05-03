package com.shorman.movies.api

import com.shorman.movies.others.Constans.API_KEY
import com.shorman.movies.api.models.movie.*
import com.shorman.movies.api.models.others.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {


    //functions for movies
    @GET("movie/popular")
    suspend fun getLatestMovies(@Query("api_key") apiKey:String = API_KEY
    , @Query("language") lang:String = "en", @Query("page") page:Int
    ):MoviesResponse

    @GET("search/movie")
    suspend fun searchForMovies(
        @Query("api_key") apiKey:String = API_KEY,
        @Query("language") lang:String = "en",
        @Query("page") page:Int,
        @Query("include_adult") adult:Boolean = true,
        @Query("query") searchKeyWord:String
    ):MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieID:Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang:String = "en",
    ):Response<MovieDetails>


    @GET("movie/{movie_id}/credits")
    suspend fun getMovieActors(
        @Path("movie_id") movieID:Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang:String= "en"
    ):Response<ActorsResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieID:Int,
        @Query("api_key") apiKey: String = API_KEY,
    ):Response<VideosResponse>

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") movieID:Int,
        @Query("api_key") apiKey: String = API_KEY,
    ):MovieImageList

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieID:Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page:Int,
        @Query("language") lang:String = "en"
    ): ReviewsResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey:String = API_KEY,
        @Query("language") lang:String = "en"
    ):Response<GenresResponse>

    @GET("discover/movie")
    suspend fun getRandomMovie(
        @Query("with_original_language") language: String="",
        @Query("with_genres") genres:String="",
        @Query("with_watch_monetization_types") watchType:String="",
        @Query("primary_release_date.gte") minimumReleaseDate:String="",
        @Query("vote_average.gte") minimumRating:Float=0f,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page:Int = 1,
        @Query("language") lang:String = "en"
    ):Response<MoviesResponse>

}