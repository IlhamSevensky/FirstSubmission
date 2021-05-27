package com.app.core.data.source.local

import com.app.core.data.source.local.entity.MovieEntity
import com.app.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    suspend fun insertFavoriteMovies(movies: List<MovieEntity>) {
        movieDao.insertFavoriteMovie(movies)
    }

    suspend fun removeFavoriteMovie(movieId: Int) {
        movieDao.removeFavoriteMovie(movieId)
    }

    fun isFavoriteMovie(movieId: Int): MovieEntity? = movieDao.isFavoriteMovie(movieId)

}