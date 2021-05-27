package com.app.core.domain.usecase.favorite_movie

interface RemoveFavoriteMovieUseCase {

    suspend fun removeFavoriteMovie(movieId: Int)

}