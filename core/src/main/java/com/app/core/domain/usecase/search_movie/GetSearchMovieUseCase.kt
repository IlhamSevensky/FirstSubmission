package com.app.core.domain.usecase.search_movie

import androidx.paging.PagingData
import com.app.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetSearchMovieUseCase {

    fun getSearchMovies(query: String): Flow<PagingData<Movie>>

}