package com.example.moviedb.service

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviedb.entity.GenreEntity

@Dao
interface GenreDao {

    @Query("SELECT * FROM genres")
    fun getGenres(): LiveData<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genres: List<GenreEntity>)

    @Delete
    suspend fun deleteGenres(genres: List<GenreEntity>)

}