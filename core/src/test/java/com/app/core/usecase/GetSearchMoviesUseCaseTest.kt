package com.app.core.usecase

import com.app.core.common.DummyData
import com.app.core.domain.repository.MovieRepository
import com.app.core.domain.usecase.search_movie.GetSearchMovieInteractor
import com.app.core.domain.usecase.search_movie.GetSearchMovieUseCase
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@RunWith(MockitoJUnitRunner::class)
class GetSearchMoviesUseCaseTest {

    private lateinit var getSearchMoviesUseCase: GetSearchMovieUseCase

    @Mock
    private lateinit var movieRepository: MovieRepository

    private val dummyMovies = flowOf(DummyData.getPagingMovies())
    private val dummyQuery = "dummy_query"

    @Before
    fun setUp(){
        getSearchMoviesUseCase = GetSearchMovieInteractor(movieRepository)
    }

    @Test
    fun `should get data from repository`() {
        Mockito.`when`(movieRepository.searchMovie(any()))
            .thenReturn(dummyMovies)

        val movies = getSearchMoviesUseCase.getSearchMovies(dummyQuery)

        verify(movieRepository).searchMovie(dummyQuery)
        verifyNoMoreInteractions(movieRepository)
        assertEquals(dummyMovies, movies)
    }

}