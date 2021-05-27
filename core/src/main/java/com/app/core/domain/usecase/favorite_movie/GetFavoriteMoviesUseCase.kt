package com.app.core.domain.usecase.favorite_movie

import com.app.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetFavoriteMoviesUseCase {

    fun getFavoriteMovies(): Flow<List<Movie>>

}