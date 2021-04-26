package com.shorman.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shorman.movies.api.models.movie.MovieModel
import com.shorman.movies.api.models.others.RemoteKeys

@Database(version = 1,entities = [MovieModel::class,RemoteKeys::class],exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getMoviesDao():MoviesDao
    abstract fun getRemoteKeysDao():RemoteKeysMoviesDao

}