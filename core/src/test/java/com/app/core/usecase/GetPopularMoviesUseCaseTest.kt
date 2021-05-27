package com.app.core.usecase

import com.app.core.common.DummyData
import com.app.core.domain.repository.MovieRepository
import com.app.core.domain.usecase.popular_movie.GetPopularMoviesInteractor
import com.app.core.domain.usecase.popular_movie.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesUseCaseTest {

    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Mock
    private lateinit var movieRepository: MovieRepository

    private val dummyMovies = flowOf(DummyData.getPagingMovies())

    @Before
    fun setUp(){
        getPopularMoviesUseCase = GetPopularMoviesInteractor(movieRepository)
    }

    @Test
    fun `should get data from repository`() {
        `when`(movieRepository.getPopularMovies())
            .thenReturn(dummyMovies)

        val movies = getPopularMoviesUseCase.getPopularMovies()

        verify(movieRepository).getPopularMovies()
        verifyNoMoreInteractions(movieRepository)
        assertEquals(dummyMovies, movies)
    }

}