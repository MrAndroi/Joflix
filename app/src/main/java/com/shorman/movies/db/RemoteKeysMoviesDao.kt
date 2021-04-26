package com.shorman.movies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shorman.movies.api.models.others.RemoteKeys

@Dao
interface RemoteKeysMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys_table_movie WHERE repoId = :id")
    suspend fun remoteKeysMovieId(id: Int): RemoteKeys?

    @Query("DELETE FROM remote_keys_table_movie")
    suspend fun clearRemoteKeys()
}