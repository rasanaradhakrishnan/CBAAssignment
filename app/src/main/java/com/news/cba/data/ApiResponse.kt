package com.news.cba.data

sealed class ApiResponse<out T> {
    data class Success<out T>(val value: T) : ApiResponse<T>()
    data class GenericError(val code: Int? = null, val error: String? = null) :
        ApiResponse<Nothing>()

    object NetworkError : ApiResponse<Nothing>()
}