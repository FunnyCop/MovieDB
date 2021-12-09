package com.example.moviedb.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreResult(

    val genres: List<Genre>

)