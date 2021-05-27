package com.app.core.domain.repository

import androidx.paging.PagingData
import com.app.core.domain.model.Movie
import com.app.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getDetailMovie(movieId: Int): Flow<Resource<Movie>>

    fun searchMovie(query: String): Flow<PagingData<Movie>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun removeFavoriteMovie(movieId: Int)

}