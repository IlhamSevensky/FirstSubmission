package com.app.core.domain.usecase.popular_movie

import androidx.paging.PagingData
import com.app.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetPopularMoviesUseCase {

    fun getPopularMovies(): Flow<PagingData<Movie>>

}