package com.app.core.domain.usecase.search_movie

import androidx.paging.PagingData
import com.app.core.domain.model.Movie
import com.app.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchMovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : GetSearchMovieUseCase {

    override fun getSearchMovies(
        query: String
    ): Flow<PagingData<Movie>> = movieRepository.searchMovie(query)

}