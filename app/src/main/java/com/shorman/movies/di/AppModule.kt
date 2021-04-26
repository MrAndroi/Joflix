package com.shorman.movies.di

import android.content.Context
import androidx.room.Room
import com.shorman.movies.others.Constans.BASE_URL
import com.shorman.movies.api.MoviesApi
import com.shorman.movies.api.TvShowsApi
import com.shorman.movies.db.AppDatabase
import com.shorman.movies.others.Constans.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoviesApi(): MoviesApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MoviesApi::class.java)

    @Singleton
    @Provides
    fun provideTvShowsApi(): TvShowsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TvShowsApi::class.java)

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,AppDatabase::class.java,DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

}