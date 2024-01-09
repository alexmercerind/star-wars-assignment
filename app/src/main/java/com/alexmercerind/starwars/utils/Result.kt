package com.alexmercerind.starwars.utils

sealed class Result<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(error: Throwable?, data: T? = null) : Result<T>(data, error)
}
