package com.app.core.di

import com.app.core.BuildConfig
import com.app.core.data.source.remote.api.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    private const val BASE_URL_TMDB = BuildConfig.BASE_URL_TMDB
    private const val BASE_URL_TMDB_HOSTNAME = BuildConfig.BASE_URL_TMDB_HOSTNAME

    @Provides
    @Named(BASE_URL_TMDB)
    fun provideBaseUrl(): String = BASE_URL_TMDB

    @Provides
    @Named(BASE_URL_TMDB_HOSTNAME)
    fun provideHostname(): String = BASE_URL_TMDB_HOSTNAME

    @Provides
    fun provideCertificate(
        @Named(BASE_URL_TMDB_HOSTNAME) hostname: String
    ): CertificatePinner = CertificatePinner.Builder().apply {
        add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
        add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
        add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
    }.build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        certificate: CertificatePinner
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        )
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        certificatePinner(certificate)
    }.build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        @Named(BASE_URL_TMDB) baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        client(okHttpClient)
        addConverterFactory(MoshiConverterFactory.create())
    }.build()

    @Singleton
    @Provides
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

}