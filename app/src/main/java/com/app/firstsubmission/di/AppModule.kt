package com.app.firstsubmission.di

import com.app.core.domain.usecase.detail_movie.GetDetailMovieInteractor
import com.app.core.domain.usecase.detail_movie.GetDetailMovieUseCase
import com.app.core.domain.usecase.favorite_movie.*
import com.app.core.domain.usecase.popular_movie.GetPopularMoviesInteractor
import com.app.core.domain.usecase.popular_movie.GetPopularMoviesUseCase
import com.app.core.domain.usecase.search_movie.GetSearchMovieInteractor
import com.app.core.domain.usecase.search_movie.GetSearchMovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun providePopularMoviesUseCase(getPopularMoviesInteractor: GetPopularMoviesInteractor): GetPopularMoviesUseCase

    @Binds
    abstract fun provideDetailMovieUseCase(getDetailMovieInteractor: GetDetailMovieInteractor): GetDetailMovieUseCase

    @Binds
    abstract fun provideFavoriteMoviesUseCase(getFavoriteMoviesInteractor: GetFavoriteMoviesInteractor): GetFavoriteMoviesUseCase

    @Binds
    abstract fun provideInsertFavoriteMovieUseCase(insertFavoriteMovieInteractor: InsertFavoriteMovieInteractor): InsertFavoriteMovieUseCase

    @Binds
    abstract fun provideRemoveFavoriteMovieUseCase(removeFavoriteMovieInteractor: RemoveFavoriteMovieInteractor): RemoveFavoriteMovieUseCase

    @Binds
    abstract fun provideGetSearchMovieUseCase(getSearchMovieInteractor: GetSearchMovieInteractor): GetSearchMovieUseCase

}