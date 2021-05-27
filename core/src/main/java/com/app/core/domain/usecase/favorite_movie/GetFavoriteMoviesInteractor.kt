package com.app.core.domain.usecase.favorite_movie

import com.app.core.domain.model.Movie
import com.app.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : GetFavoriteMoviesUseCase {

    override fun getFavoriteMovies(): Flow<List<Movie>>  = movieRepository.getFavoriteMovies()

}