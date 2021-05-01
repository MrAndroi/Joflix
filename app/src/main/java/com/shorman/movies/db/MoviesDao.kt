package com.shorman.movies.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.shorman.movies.api.models.movie.MovieModel

@Dao
interface MoviesDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(moviesList:ArrayList<MovieModel>)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovie(movie:MovieModel)

    @Query("SELECT * FROM movies_table")
    fun getAllMovies():LiveData<List<MovieModel>>

    @Delete
    suspend fun deleteMovie(movie: MovieModel)

    @Query("SELECT id FROM movies_table WHERE :movieID==id")
    suspend fun checkIfMovieSaved(movieID:Int):Int?

    @Query("DELETE FROM movies_table")
    suspend fun clearAllMovies()

}