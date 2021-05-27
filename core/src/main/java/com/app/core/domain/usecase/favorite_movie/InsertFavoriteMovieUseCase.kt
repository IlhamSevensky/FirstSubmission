package com.app.core.domain.usecase.favorite_movie

import com.app.core.domain.model.Movie

interface InsertFavoriteMovieUseCase {

    suspend fun insertFavoriteMovie(movie: Movie)

}