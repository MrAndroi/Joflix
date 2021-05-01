package com.shorman.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shorman.movies.api.models.movie.MovieModel
import com.shorman.movies.api.models.others.RemoteKeys
import com.shorman.movies.api.models.tvshows.TvShowModel

@Database(version = 1,entities = [MovieModel::class,RemoteKeys::class,TvShowModel::class],exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getMoviesDao():MoviesDao
    abstract fun getTvShowsDao():TvShowsDao
    abstract fun getRemoteKeysDao():RemoteKeysMoviesDao

}