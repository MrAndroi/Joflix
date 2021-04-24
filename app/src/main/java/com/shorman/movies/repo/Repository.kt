package com.shorman.movies.repo


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.shorman.movies.moviesApi.CommentsPagingSource
import com.shorman.movies.moviesApi.MoviesApi
import com.shorman.movies.moviesApi.MoviesPagingSource
import javax.inject.Inject

class Repository @Inject constructor(private val moviesApi: MoviesApi) {

    fun searchForMovies(searchKeyword:String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 40,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(moviesApi, searchKeyword) }
        ).liveData

    fun getMovieComments(movieID: Int) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 40,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {CommentsPagingSource(moviesApi,movieID)}
    ).liveData

    suspend fun getMovieDetails(movieID:Int) = moviesApi.getMovieDetails(movieID = movieID)

    suspend fun getMovieActors(movieID: Int) = moviesApi.getMovieActors(movieID = movieID)

    suspend fun getMovieVideos(movieID: Int) = moviesApi.getMovieVideos(movieID = movieID)

    suspend fun getMovieImages(movieID: Int) = moviesApi.getMovieImages(movieID = movieID)

}