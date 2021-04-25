package com.shorman.movies.api.pagingSource

import androidx.paging.PagingSource
import com.shorman.movies.api.TvShowsApi
import com.shorman.movies.api.models.tvshows.TvShowModel
import com.shorman.movies.others.Constans
import retrofit2.HttpException
import java.io.IOException

private const val TV_SHOW_STARTING_PAGE_INDEX = 1

class TvShowsPagingSource(private val tvShowsApi: TvShowsApi, private val query:String) : PagingSource<Int, TvShowModel>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowModel> {
        val position = params.key  ?: TV_SHOW_STARTING_PAGE_INDEX

        return try {
            if(query == ""){
                val response = tvShowsApi.getPopularTvShows(page = position)
                val tvShows = response.results
                tvShows.map {
                    it.poster_path = Constans.IMAGES_BASE_URL +it.poster_path
                }

                LoadResult.Page(
                    data = tvShows,
                    prevKey = if (position == TV_SHOW_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (tvShows.isEmpty()) null else position + 1
                )
            }
            else{
                val response = tvShowsApi.searchForTvShow(searchKeyWord = query,page = position)
                val tvShows = response.results
                tvShows.map {
                    it.poster_path = Constans.IMAGES_BASE_URL +it.poster_path
                }

                LoadResult.Page(
                    data = tvShows,
                    prevKey = if (position == TV_SHOW_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (tvShows.isEmpty()) null else position + 1
                )
            }
        }catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}