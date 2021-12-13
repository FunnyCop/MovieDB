package com.example.moviedb.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.entity.MovieEntity
import com.example.moviedb.service.MovieDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalMovieRepo @Inject constructor(

    private val movieDao: MovieDao

) {

    private val _movies = MutableLiveData<List<MovieEntity>?>(null)
    val movies: LiveData<List<MovieEntity>?> get() = _movies

    fun getAllMovies() = _movies.postValue(movieDao.getAllMovies().value)

    fun searchMovies(title: String) = _movies.postValue(movieDao.searchMovies("$title%").value)

    suspend fun addMovie(movie: MovieEntity) = movieDao.addMovie(movie)

    suspend fun addMovies(movies: List<MovieEntity>) = movieDao.addMovies(movies)

    suspend fun deleteMovie(movie: MovieEntity) = movieDao.deleteMovie(movie)

    suspend fun deleteMovies(movies: List<MovieEntity>) = movieDao.deleteMovies(movies)

}