package com.shorman.movies.api.pagingSource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shorman.movies.api.MoviesApi
import com.shorman.movies.api.models.movie.MovieModel
import com.shorman.movies.api.models.others.RemoteKeys
import com.shorman.movies.db.AppDatabase
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

private const val MOVIE_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class MoviesMediator(private val moviesApi: MoviesApi,private val database: AppDatabase,private val searchQuery:String)
    :RemoteMediator<Int,MovieModel>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieModel>): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            if(searchQuery == ""){
                val response = moviesApi.getLatestMovies(page = page)
                val moviesList = response.results
                val isEndOfList = moviesList.isEmpty()
                database.withTransaction {
                    // clear all tables in the database
                    if (loadType == LoadType.REFRESH) {
                        database.getRemoteKeysDao().clearRemoteKeys()
                        database.getMoviesDao().clearAllMovies()
                    }
                    val prevKey = if (page == MOVIE_STARTING_PAGE_INDEX) null else page - 1
                    val nextKey = if (isEndOfList) null else page + 1
                    val keys = moviesList.map {
                        RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    database.getRemoteKeysDao().insertAll(keys)
                    database.getMoviesDao().insertAllMovies(moviesList)
                }
                return MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }
            else{
                val response = moviesApi.searchForMovies(page = page,searchKeyWord = searchQuery)
                val moviesList = response.results
                val isEndOfList = moviesList.isEmpty()
                database.withTransaction {
                    // clear all tables in the database
                    if (loadType == LoadType.REFRESH) {
                        database.getRemoteKeysDao().clearRemoteKeys()
                        database.getMoviesDao().clearAllMovies()
                    }
                    val prevKey = if (page == MOVIE_STARTING_PAGE_INDEX) null else page - 1
                    val nextKey = if (isEndOfList) null else page + 1
                    val keys = moviesList.map {
                        RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    database.getRemoteKeysDao().insertAll(keys)
                    database.getMoviesDao().insertAllMovies(moviesList)
                }
                return MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, MovieModel>):RemoteKeys?{
        return state.pages
            .firstOrNull{ it.data.isNotEmpty()}
            ?.data?.firstOrNull()
            ?.let{movie ->
                database.getRemoteKeysDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieModel>):RemoteKeys?{
        return state.pages
            .lastOrNull{ it.data.isNotEmpty()}
            ?.data?.lastOrNull()
            ?.let{movie ->
                database.getRemoteKeysDao().remoteKeysMovieId(movie.id)
            }

    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, MovieModel>):RemoteKeys?{
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id?.let {remoteKey ->
                database.getRemoteKeysDao().remoteKeysMovieId(remoteKey)
            }
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, MovieModel>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: MOVIE_STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                //end of list condition reached
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

}


