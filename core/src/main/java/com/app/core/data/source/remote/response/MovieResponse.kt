package com.app.core.data.source.remote.response

data class MovieResponse(
    val id: Int,
    val title: String,
    val overview: String?,
    val release_date: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Float?
)