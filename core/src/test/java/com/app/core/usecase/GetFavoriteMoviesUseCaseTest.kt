package com.app.core.usecase

import com.app.core.common.DummyData
import com.app.core.domain.repository.MovieRepository
import com.app.core.domain.usecase.favorite_movie.GetFavoriteMoviesInteractor
import com.app.core.domain.usecase.favorite_movie.GetFavoriteMoviesUseCase
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class GetFavoriteMoviesUseCaseTest {

    private lateinit var getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase

    @Mock
    private lateinit var movieRepository: MovieRepository

    private val dummyMovies = flowOf(DummyData.getMovies())

    @Before
    fun setUp(){
        getFavoriteMoviesUseCase = GetFavoriteMoviesInteractor(movieRepository)
    }

    @Test
    fun `should get data from repository`() {
        `when`(movieRepository.getFavoriteMovies())
            .thenReturn(dummyMovies)

        val movies = getFavoriteMoviesUseCase.getFavoriteMovies()

        verify(movieRepository).getFavoriteMovies()
        verifyNoMoreInteractions(movieRepository)
        Assert.assertEquals(dummyMovies, movies)
    }

}