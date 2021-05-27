package com.app.core.utils

import com.app.core.BuildConfig

object Constants {

    const val DEFAULT_PAGE_START = 1
    const val DEFAULT_ITEM_PER_PAGE = 20
    const val API_KEY = BuildConfig.TMDB_API_KEY
    const val BASE_URL_IMAGE = BuildConfig.BASE_URL_IMAGE_TMDB
    const val ENDPOINT_POSTER_SIZE_W185 = "w185"
    const val ENDPOINT_POSTER_SIZE_W780 = "w780"

    const val FAVORITE_MODULE = "favorite"
    const val SEARCH_MODULE = "search"

}