package com.app.core.di

import com.app.core.BuildConfig
import com.app.core.data.source.remote.api.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL_TMDB)
        addConverterFactory(MoshiConverterFactory.create())
    }.build()

    @Singleton
    @Provides
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

}