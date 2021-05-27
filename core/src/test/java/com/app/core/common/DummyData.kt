package com.app.core.common

import androidx.paging.PagingData
import com.app.core.domain.model.Movie

object DummyData {

    fun getMovies(): List<Movie> = listOf(
        Movie(
            1,
            "title",
            "desc",
            "date",
            null,
            null,
            0F,
            false
        ),
        Movie(
            2,
            "title",
            "desc",
            "date",
            null,
            null,
            0F,
            false
        ),
    )

    fun getPagingMovies(): PagingData<Movie> = PagingData.from(getMovies())

}