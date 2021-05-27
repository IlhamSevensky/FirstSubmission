package com.app.firstsubmission.di

import com.app.core.domain.repository.MovieRepository
import com.app.core.domain.usecase.search_movie.GetSearchMovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface SearchFeatureDependencies {

    fun getMovieRepository(): MovieRepository

    fun getSearchMovieUseCase(): GetSearchMovieUseCase

}