package com.example.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.entity.MovieEntity
import com.example.moviedb.repo.LocalMovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalMovieViewModel @Inject constructor(

    private val localMovieRepo: LocalMovieRepo

) : ViewModel() {

    val movies = localMovieRepo.movies

    fun getAllMovies() =
        localMovieRepo.getAllMovies()

    fun searchMovies(title: String) =
        localMovieRepo.searchMovies(title)

    fun addMovie(movie: MovieEntity) =
        viewModelScope.launch(Dispatchers.IO) { localMovieRepo.addMovie(movie) }

    fun addMovies(movies: List<MovieEntity>) =
        viewModelScope.launch(Dispatchers.IO) { localMovieRepo.addMovies(movies) }

    fun deleteMovie(movie: MovieEntity) =
        viewModelScope.launch(Dispatchers.IO) { localMovieRepo.deleteMovie(movie) }

    fun deleteMovies(movies: List<MovieEntity>) =
        viewModelScope.launch(Dispatchers.IO) { localMovieRepo.deleteMovies(movies) }

}