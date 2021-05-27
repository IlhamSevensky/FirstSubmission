package com.app.core.data.source.remote

import androidx.paging.PagingSource
import com.app.core.data.source.remote.response.MovieResponse
import com.app.core.utils.Constants.DEFAULT_PAGE_START
import com.squareup.moshi.JsonDataException
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesPagingSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, MovieResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val pagePosition = params.key ?: DEFAULT_PAGE_START

        try {

            val response = remoteDataSource.getPopularMovies(pagePosition)

            return LoadResult.Page(
                data = response.results,
                prevKey = if (pagePosition == DEFAULT_PAGE_START) null else pagePosition - 1,
                nextKey = if (response.results.isEmpty()) null else pagePosition + 1
            )

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: JsonDataException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}