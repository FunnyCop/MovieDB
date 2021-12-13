package com.example.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.entity.GenreEntity
import com.example.moviedb.repo.LocalGenreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalGenreViewModel @Inject constructor(

    private val localGenreRepo: LocalGenreRepo

) : ViewModel() {

    val genres = localGenreRepo.genres

    fun addGenres(genres: List<GenreEntity>) =
        viewModelScope.launch(Dispatchers.IO) { localGenreRepo.addGenres(genres) }

    fun deleteGenres(genres: List<GenreEntity>) =
        viewModelScope.launch(Dispatchers.IO) { localGenreRepo.deleteGenres(genres) }

}