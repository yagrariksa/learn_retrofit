package com.practice.retrofit.network

sealed class Result<out T: Any> {
    data class Success<out T: Any>(val data: T): Result<T>()
    data class Error(val exception: String): Result<Nothing>()
    data class ErrorResponse<out T: Any>(val exception: T): Result<T>()
}