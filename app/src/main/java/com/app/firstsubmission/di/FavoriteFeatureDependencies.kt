package com.app.firstsubmission.di

import com.app.core.domain.repository.MovieRepository
import com.app.core.domain.usecase.favorite_movie.GetFavoriteMoviesUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteFeatureDependencies {

    fun getMovieRepository(): MovieRepository

    fun getFavoriteMoviesUseCase(): GetFavoriteMoviesUseCase

}