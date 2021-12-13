package com.example.moviedb.repo

import androidx.lifecycle.LiveData
import com.example.moviedb.entity.GenreEntity
import com.example.moviedb.service.GenreDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalGenreRepo @Inject constructor(

    private val genreDao: GenreDao

) {

    val genres: LiveData<List<GenreEntity>> = genreDao.getGenres()

    suspend fun addGenres(genres: List<GenreEntity>) = genreDao.addGenres(genres)

    suspend fun deleteGenres(genres: List<GenreEntity>) = genreDao.deleteGenres(genres)

}