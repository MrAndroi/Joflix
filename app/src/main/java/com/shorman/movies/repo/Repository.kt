package com.shorman.movies.repo


import androidx.paging.*
import com.shorman.movies.api.MoviesApi
import com.shorman.movies.api.TvShowsApi
import com.shorman.movies.api.pagingSource.*
import com.shorman.movies.db.AppDatabase
import javax.inject.Inject

class Repository @Inject constructor(private val moviesApi: MoviesApi,private val tvShowsApi: TvShowsApi,private val database: AppDatabase) {

    fun searchForMovies(searchKeyword:String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 40,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(moviesApi, searchKeyword) }
        ).liveData

    @ExperimentalPagingApi
    fun searchForMovies2(searchKeyword: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 40,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {database.getMoviesDao().getAllMovies()},
            remoteMediator = MoviesMediator(moviesApi,database,searchKeyword)
        ).liveData

    fun getMovieComments(movieID: Int) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 40,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CommentsPagingSource(moviesApi,movieID) }
    ).liveData

    suspend fun getMovieDetails(movieID:Int) = moviesApi.getMovieDetails(movieID = movieID)

    suspend fun getMovieActors(movieID: Int) = moviesApi.getMovieActors(movieID = movieID)

    suspend fun getMovieVideos(movieID: Int) = moviesApi.getMovieVideos(movieID = movieID)

    suspend fun getMovieImages(movieID: Int) = moviesApi.getMovieImages(movieID = movieID)

    suspend fun getMovieGenres() = moviesApi.getMovieGenres()

    suspend fun getRandomMovie(
        language: String ="",
        genres:String ="",
        watchType:String ="",
        minimumRealseDate:String="",
        minimumRating:Float=0f,
    ) = moviesApi.getRandomMovie(
        language,genres,watchType,minimumRealseDate,minimumRating
    )

    //////////////////////STARTING TV SHOWS FUNCTIONS HERE/////////////////////////////

    fun searchForTvShows(searchKeyword: String) =
        Pager(
            PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {TvShowsPagingSource(tvShowsApi,searchKeyword)}
        ).liveData

    fun getTvShowReviews(tvShowID: Int) =
        Pager(
            PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {TvShowsCommentsPagingSource(tvShowsApi,tvShowID)}
        ).liveData

    suspend fun getTvShowDetails(tvShowID:Int) = tvShowsApi.getTvShowDetails(tvShowID = tvShowID)

    suspend fun getTvShowVideos(tvShowID: Int) = tvShowsApi.getTvShowVideos(tvShowID)

    suspend fun getRandomTvShow(
        language: String ="",
        genres:String ="",
        watchType:String ="",
        minimumRealseDate:String="",
        minimumRating:Float=0f,
    ) = tvShowsApi.getRandomTvShow(
        language,genres,watchType,minimumRealseDate,minimumRating
    )

}