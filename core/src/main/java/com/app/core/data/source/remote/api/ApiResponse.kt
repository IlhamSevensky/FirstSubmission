package com.app.core.data.source.remote.api

import okio.IOException
import retrofit2.HttpException

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception:Exception) : ApiResponse<Nothing>()
    data class HttpError(val exception: HttpException) : ApiResponse<Nothing>()
    data class ConnectionError(val exception: IOException) : ApiResponse<Nothing>()
}