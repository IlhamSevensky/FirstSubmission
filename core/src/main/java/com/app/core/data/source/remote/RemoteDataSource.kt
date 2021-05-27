package com.app.core.data.source.remote

import com.app.core.data.source.remote.api.ApiResponse
import com.app.core.data.source.remote.api.MovieApiService
import com.app.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) {

    suspend fun getPopularMovies(
        page: Int
    ) = movieApiService.getPopularMovies(page = page)

    suspend fun getDetailMovie(
        movieId: Int
    ): Flow<ApiResponse<MovieResponse>> = flow {
        return@flow try {
            val response = movieApiService.getDetailMovie(movieId = movieId)

            emit(ApiResponse.Success(response))

        } catch (e: IOException) {
            emit(ApiResponse.ConnectionError(e))
        } catch (e: HttpException) {
            emit(ApiResponse.HttpError(e))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e))
        }

    }.flowOn(IO)

    suspend fun searchMovie(
        query: String,
        page: Int
    ) = movieApiService.searchMovie(query = query, page = page)
}