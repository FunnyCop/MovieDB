package com.example.moviedb.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.model.Genre
import com.example.moviedb.model.GenreResult
import com.example.moviedb.model.Movie
import com.example.moviedb.model.MovieResult
import com.example.moviedb.repo.TMDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TMDBViewModel @Inject constructor(

    private val tmdbRepo: TMDBRepo

) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>?>(null)
    val movies: LiveData<List<Movie>?> get() = _movies

    private val _genres = MutableLiveData<List<Genre>>(null)
    val genres: LiveData<List<Genre>?> get() = _genres

    private val _search = MutableLiveData<String?>(null)
    val search: LiveData<String?> get() = _search

    private val _pages = MutableLiveData<Int?>(null)
    val pages: LiveData<Int?> get() = _pages

    /**
     * Get movies ordered by popularity.
     * @param page The results page ([Int]).
     */
    fun getPopularMovies(page: Int) =
        viewModelScope.launch(Dispatchers.IO) {

            _movies.postValue(null)

            tmdbRepo.getPopularMovies(page)?.let {

                _movies.postValue(it.results)
                _pages.postValue(it.totalPages)

            }

            _search.postValue(null)

        }

    /**
     * Get movies ordered by popularity and add them to existing results.
     * @param page The results page ([Int]).
     */
    fun getMorePopularMovies(page: Int) =
        viewModelScope.launch(Dispatchers.IO) {

            tmdbRepo.getPopularMovies(page)?.let { result ->

                _movies.value?.let {

                    val newMovies: List<Movie> = it.toMutableList().apply { addAll(result.results) }

                    _movies.postValue(newMovies)

                }

            }

        }

    /** Get all genres. */
    fun getGenres() =
        viewModelScope.launch(Dispatchers.IO) {

            tmdbRepo.getGenres()?.let { _genres.postValue(it.genres) }

        }

    /**
     * Get movies by search query.
     * @param query Name of movie to search for ([String]).
     * @param page The results page ([Int]).
     */
    fun searchMovies(query: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {

            _movies.postValue(null)

            tmdbRepo.searchMovies(query, page)?.let {

                _movies.postValue(it.results)
                _pages.postValue(it.totalPages)

            }

            _search.postValue(query)

        }

    /**
     * Get movies by search query and add them to existing results.
     * @param query Name of movie to search for ([String]).
     * @param page The results page ([Int]).
     */
    fun searchMoreMovies(query: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {

            tmdbRepo.searchMovies(query, page)?.let { result ->

                _movies.value?.let {

                    val newMovies: List<Movie> = it.toMutableList().apply { addAll(result.results) }

                    _movies.postValue(newMovies)

                }

            }

        }

}