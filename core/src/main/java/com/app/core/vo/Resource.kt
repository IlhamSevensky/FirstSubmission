package com.app.core.vo

import okio.IOException
import retrofit2.HttpException

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val exception: Exception, val data: T? = null) : Resource<T>()
    data class HttpError<out T>(val exception: HttpException, val data: T? = null) :
        Resource<T>()
    data class ConnectionError<out T>(val exception: IOException, val data: T? = null) : Resource<T>()
    data class Loading<out T>(val data: T? = null) : Resource<T>()

}