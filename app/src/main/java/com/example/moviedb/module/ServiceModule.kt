package com.example.moviedb.module

import com.example.moviedb.service.TMDBService
import com.example.moviedb.util.TMDB_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideTMDBService() = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create<TMDBService>()

}