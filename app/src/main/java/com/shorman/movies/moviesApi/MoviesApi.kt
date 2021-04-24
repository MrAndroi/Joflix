package com.shorman.movies.moviesApi

import com.shorman.movies.Constans.API_KEY
import com.shorman.movies.moviesApi.models.movie.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

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
    ):Response<MovieImageList>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieID:Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page:Int,
    ):ReviewsResponse

}