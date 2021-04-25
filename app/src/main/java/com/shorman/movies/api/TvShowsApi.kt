package com.shorman.movies.api

import com.shorman.movies.api.models.others.ReviewsResponse
import com.shorman.movies.api.models.others.VideosResponse
import com.shorman.movies.api.models.tvshows.TvShowDetails
import com.shorman.movies.api.models.tvshows.TvShowsResponse
import com.shorman.movies.others.Constans.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsApi {

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey:String = API_KEY,
        @Query("page") page:Int
    ):TvShowsResponse

    @GET("search/tv")
    suspend fun searchForTvShow(
        @Query("api_key") apiKey:String = API_KEY,
        @Query("language") lang:String = "en",
        @Query("page") page:Int,
        @Query("include_adult") adult:Boolean = true,
        @Query("query") searchKeyWord:String
    ):TvShowsResponse


    @GET("tv/{tv_id}")
    suspend fun getTvShowDetails(
        @Path("tv_id") tvShowID:Int,
        @Query("api_key") apiKey: String = API_KEY,
    ):Response<TvShowDetails>

    @GET("tv/{tv_id}/videos")
    suspend fun getTvShowVideos(
        @Path("tv_id") movieID:Int,
        @Query("api_key") apiKey: String = API_KEY,
    ):Response<VideosResponse>

    @GET("tv/{tv_id}/reviews")
    suspend fun getTvShowReviews(
        @Path("tv_id") movieID:Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page:Int,
    ): ReviewsResponse

}