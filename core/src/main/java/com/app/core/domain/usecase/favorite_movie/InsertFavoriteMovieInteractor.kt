package com.app.core.domain.usecase.favorite_movie

import com.app.core.domain.model.Movie
import com.app.core.domain.repository.MovieRepository
import javax.inject.Inject

class InsertFavoriteMovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : InsertFavoriteMovieUseCase {

    override suspend fun insertFavoriteMovie(
        movie: Movie
    ) = movieRepository.insertFavoriteMovie(movie)

}