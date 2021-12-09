package com.example.moviedb.service

import com.example.moviedb.model.GenreResult
import com.example.moviedb.model.MovieResult
import com.example.moviedb.util.TMDB_API_KEY_V3
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    /**
     * Get movies ordered by popularity.
     * @param page The results page ([Int]).
     * @return HTTP [Response] with [MovieResult]
     */
    @GET("movie/popular?api_key=$TMDB_API_KEY_V3&language=en-US")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieResult>

    /**
     * Get all genres.
     * @return HTTP [Response] with [GenreResult].
     */
    @GET("genre/movie/list?api_key=$TMDB_API_KEY_V3&language=en-US")
    suspend fun getGenres(): Response<GenreResult>

    /**
     * Get movies by search query.
     * @param query Name of movie to search for ([String]).
     * @param page The results page ([Int]).
     * @return HTTP [Response] with [MovieResult].
     */
    @GET("search/movie?api_key=$TMDB_API_KEY_V3&language=en-US")
    suspend fun searchMovies(

        @Query("query") query: String,
        @Query("page") page: Int

    ): Response<MovieResult>

}