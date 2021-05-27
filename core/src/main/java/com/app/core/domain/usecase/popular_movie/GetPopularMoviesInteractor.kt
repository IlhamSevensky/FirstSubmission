package com.app.core.domain.usecase.popular_movie

import androidx.paging.PagingData
import com.app.core.domain.model.Movie
import com.app.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesInteractor @Inject constructor(private val movieRepository: MovieRepository):
    GetPopularMoviesUseCase {

    override fun getPopularMovies(): Flow<PagingData<Movie>> = movieRepository.getPopularMovies()

}