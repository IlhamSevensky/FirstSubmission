package com.app.core.domain.usecase.favorite_movie

import com.app.core.domain.repository.MovieRepository
import javax.inject.Inject

class RemoveFavoriteMovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : RemoveFavoriteMovieUseCase {

    override suspend fun removeFavoriteMovie(
        movieId: Int
    ) = movieRepository.removeFavoriteMovie(movieId)

}