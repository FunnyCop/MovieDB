package com.example.moviedb.service

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviedb.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE title like :title")
    fun searchMovies(title: String): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieEntity>)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovies(movies: List<MovieEntity>)

}