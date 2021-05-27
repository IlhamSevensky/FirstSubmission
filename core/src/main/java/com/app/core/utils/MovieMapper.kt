package com.app.core.utils

import com.app.core.data.source.local.entity.MovieEntity
import com.app.core.data.source.remote.response.MovieResponse
import com.app.core.domain.model.Movie
import com.app.core.utils.Constants.BASE_URL_IMAGE
import com.app.core.utils.Constants.ENDPOINT_POSTER_SIZE_W185
import com.app.core.utils.Constants.ENDPOINT_POSTER_SIZE_W780

object MovieMapper {

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map { mapEntityToDomain(it) }

    private fun mapEntityToDomain(input: MovieEntity): Movie = Movie(
        id = input.id,
        title = input.title,
        overview = input.overview,
        release_date = input.release_date ?: "-",
        poster_path = input.poster_path,
        backdrop_path = input.backdrop_path,
        vote_average = input.vote_average ?: 0F,
        isFavorite = input.isFavorite,
    )

    fun mapDomainToEntity(input: Movie): MovieEntity = MovieEntity(
        id = input.id,
        title = input.title,
        overview = input.overview,
        release_date = input.release_date,
        poster_path = input.poster_path,
        backdrop_path = input.backdrop_path,
        vote_average = input.vote_average,
        isFavorite = input.isFavorite,
    )

    fun mapResponseToDomain(input: MovieResponse): Movie = Movie(
        id = input.id,
        title = input.title,
        overview = input.overview ?: "-",
        release_date = input.release_date ?: "-",
        poster_path = input.poster_path?.let { BASE_URL_IMAGE + ENDPOINT_POSTER_SIZE_W185 + it },
        backdrop_path = input.backdrop_path?.let { BASE_URL_IMAGE + ENDPOINT_POSTER_SIZE_W780 + it },
        vote_average = input.vote_average ?: 0F,
        isFavorite = false,
    )

}