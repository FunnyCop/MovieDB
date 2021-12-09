package com.example.moviedb.repo

import com.example.moviedb.model.GenreResult
import com.example.moviedb.model.MovieResult
import com.example.moviedb.service.TMDBService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TMDBRepo @Inject constructor(

    private val tmdbService: TMDBService

) {

    /**
     * Get movies ordered by popularity.
     * @param page The results page ([Int]).
     * @return [MovieResult] (Nullable).
     */
    suspend fun getPopularMovies(page: Int) =
        tmdbService.getPopularMovies(page).body()

    /**
     * Get all genres.
     * @return [GenreResult] (Nullable).
     */
    suspend fun getGenres() =
        tmdbService.getGenres().body()

    /**
     * Get movies by search query.
     * @param query Name of movie to search for ([String]).
     * @param page The results page ([Int]).
     * @return [MovieResult] (Nullable).
     */
    suspend fun searchMovies(query: String, page: Int) =
        tmdbService.searchMovies(query, page).body()

}