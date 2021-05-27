package com.app.core.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
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

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}