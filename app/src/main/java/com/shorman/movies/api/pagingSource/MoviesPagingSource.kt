package com.shorman.movies.api.pagingSource

import androidx.paging.PagingSource
import com.shorman.movies.api.MoviesApi
import com.shorman.movies.others.Constans.IMAGES_BASE_URL
import com.shorman.movies.api.models.movie.MovieModel
import retrofit2.HttpException
import java.io.IOException

private const val MOVIE_STARTING_PAGE_INDEX = 1

class MoviesPagingSource(private val moviesApi: MoviesApi, private val query:String) : PagingSource<Int,MovieModel>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        val position = params.key  ?: MOVIE_STARTING_PAGE_INDEX

        return try {
            if(query == ""){
                val response = moviesApi.getLatestMovies(page = position)
                val movies = response.results
                LoadResult.Page(
                        data = movies,
                        prevKey = if (position == MOVIE_STARTING_PAGE_INDEX) null else position - 1,
                        nextKey = if (movies.isEmpty()) null else position + 1
                )
            }
            else{
                val response = moviesApi.searchForMovies(searchKeyWord = query,page = position)
                val movies = response.results
                movies.map {
                    it.poster_path = IMAGES_BASE_URL+it.poster_path
                }

                LoadResult.Page(
                    data = movies,
                    prevKey = if (position == MOVIE_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (movies.isEmpty()) null else position + 1
                )
            }
        }catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}