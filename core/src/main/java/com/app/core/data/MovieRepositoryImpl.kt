package com.app.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.core.data.source.local.LocalDataSource
import com.app.core.data.source.remote.PopularMoviesPagingSource
import com.app.core.data.source.remote.RemoteDataSource
import com.app.core.data.source.remote.SearchMoviesPagingSource
import com.app.core.data.source.remote.api.ApiResponse
import com.app.core.domain.model.Movie
import com.app.core.domain.repository.MovieRepository
import com.app.core.utils.Constants.DEFAULT_ITEM_PER_PAGE
import com.app.core.utils.MovieMapper
import com.app.core.vo.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MovieRepository {

    override fun getPopularMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = DEFAULT_ITEM_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { PopularMoviesPagingSource(remoteDataSource) }
    ).flow
        .map { movieResponse ->
            movieResponse.mapSync { response ->
                MovieMapper.mapResponseToDomain(response)
            }
        }
        .flowOn(IO)

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading())

        remoteDataSource.getDetailMovie(movieId).collect { state ->

            when (state) {
                is ApiResponse.Success -> {
                    val data = MovieMapper.mapResponseToDomain(state.data)
                    val isFavorite = localDataSource.isFavoriteMovie(data.id)
                    isFavorite?.let { data.isFavorite = true }
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> emit(Resource.Error(state.exception, data = null))
                is ApiResponse.HttpError -> emit(Resource.Error(state.exception, data = null))
                is ApiResponse.ConnectionError -> emit(Resource.Error(state.exception, data = null))
            }

        }

    }.flowOn(IO)

    override fun searchMovie(query: String): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = DEFAULT_ITEM_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { SearchMoviesPagingSource(query, remoteDataSource) }
    ).flow
        .map { movieResponse ->
            movieResponse.mapSync { response ->
                MovieMapper.mapResponseToDomain(response)
            }
        }.flowOn(IO)

    override fun getFavoriteMovies(): Flow<List<Movie>> = flow {
        localDataSource.getFavoriteMovies().collect { movies ->
            val domainMovie = MovieMapper.mapEntitiesToDomain(movies)
            emit(domainMovie)
        }
    }.flowOn(IO)

    override suspend fun insertFavoriteMovie(movie: Movie) {
        movie.isFavorite = true
        val movieEntity = MovieMapper.mapDomainToEntity(movie)
        localDataSource.insertFavoriteMovies(listOf(movieEntity))
    }

    override suspend fun removeFavoriteMovie(movieId: Int) {
        localDataSource.removeFavoriteMovie(movieId)
    }

}