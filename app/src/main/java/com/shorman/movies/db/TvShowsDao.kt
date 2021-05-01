package com.shorman.movies.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shorman.movies.api.models.tvshows.TvShowModel

@Dao
interface TvShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTvShows(tvShowList:ArrayList<TvShowModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: TvShowModel)

    @Query("SELECT * FROM tv_shows_table")
    fun getAllTvShows(): LiveData<List<TvShowModel>>

    @Delete
    suspend fun deleteTvShow(tvShow: TvShowModel)

    @Query("SELECT id FROM tv_shows_table WHERE :showID==id")
    suspend fun checkIfTvShowSaved(showID:Int):Int?

    @Query("DELETE FROM tv_shows_table")
    suspend fun clearAllTvShows()
}