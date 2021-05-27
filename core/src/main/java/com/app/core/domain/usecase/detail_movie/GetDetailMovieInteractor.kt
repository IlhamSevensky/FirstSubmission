package com.app.core.domain.usecase.detail_movie

import com.app.core.domain.model.Movie
import com.app.core.domain.repository.MovieRepository
import com.app.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailMovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
): GetDetailMovieUseCase {

    override fun getDetailMovie(
        movieId: Int
    ): Flow<Resource<Movie>> = movieRepository.getDetailMovie(movieId)

}