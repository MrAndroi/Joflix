package com.shorman.movies.api.pagingSource

import androidx.paging.PagingSource
import com.shorman.movies.api.MoviesApi
import com.shorman.movies.api.models.others.Review
import retrofit2.HttpException
import java.io.IOException

private const val COMMENT_STARTING_PAGE_INDEX = 1

class CommentsPagingSource(private val moviesApi: MoviesApi, private val movieID:Int):PagingSource<Int, Review>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val position = params.key ?: COMMENT_STARTING_PAGE_INDEX

        return try {
            val response = moviesApi.getMovieReviews(movieID = movieID, page = position)
            val comments = response.results
            LoadResult.Page(
                data = comments,
                prevKey = if (position == COMMENT_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (comments.isEmpty()) null else position + 1
            )
        }
        catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }


}