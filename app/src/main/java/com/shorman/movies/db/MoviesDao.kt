package com.shorman.movies.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.shorman.movies.api.models.movie.MovieModel

@Dao
interface MoviesDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(moviesList:ArrayList<MovieModel>)

    @Query("SELECT * FROM movies_table")
    fun getAllMovies():PagingSource<Int,MovieModel>

    @Query("DELETE FROM movies_table")
    suspend fun clearAllMovies()

}