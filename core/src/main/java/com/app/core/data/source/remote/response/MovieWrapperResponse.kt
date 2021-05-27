package com.app.core.data.source.remote.response

data class MovieWrapperResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieResponse>
)