package com.app.core.domain.usecase.detail_movie

import com.app.core.domain.model.Movie
import com.app.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface GetDetailMovieUseCase {

    fun getDetailMovie(movieId: Int) : Flow<Resource<Movie>>

}