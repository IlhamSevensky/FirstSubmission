package com.app.core.usecase

import com.app.core.common.DummyData
import com.app.core.domain.repository.MovieRepository
import com.app.core.domain.usecase.detail_movie.GetDetailMovieInteractor
import com.app.core.domain.usecase.detail_movie.GetDetailMovieUseCase
import com.app.core.vo.Resource
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
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
class GetDetailMovieUseCaseTest {

    private lateinit var getDetailMovieUseCase: GetDetailMovieUseCase

    @Mock
    private lateinit var movieRepository: MovieRepository

    private val dummyId = 0
    private val dummyMovie = flowOf(
        Resource.Success(DummyData.getMovies().first())
    )

    @Before
    fun setUp(){
        getDetailMovieUseCase = GetDetailMovieInteractor(movieRepository)
    }

    @Test
    fun `should get data from repository`() {
        Mockito.`when`(movieRepository.getDetailMovie(any()))
            .thenReturn(dummyMovie)

        val movie = getDetailMovieUseCase.getDetailMovie(dummyId)

        verify(movieRepository).getDetailMovie(dummyId)
        verifyNoMoreInteractions(movieRepository)
        Assert.assertEquals(dummyMovie, movie)

    }

}