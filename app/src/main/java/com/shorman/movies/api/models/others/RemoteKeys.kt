package com.shorman.movies.api.models.others

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shorman.movies.others.Constans.REMOTE_KEYS_TABLE_MOVIE

@Entity(tableName = REMOTE_KEYS_TABLE_MOVIE)
data class RemoteKeys(@PrimaryKey val repoId: Int, val prevKey: Int?, val nextKey: Int?)
