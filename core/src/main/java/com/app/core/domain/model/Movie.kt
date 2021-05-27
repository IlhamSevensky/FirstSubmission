package com.app.core.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Float,
    var isFavorite: Boolean
)