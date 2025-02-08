package com.sergionaude.marvelheroes.data.api

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Initial<T>(): NetworkResult<T>()
    class Success<T>(data: T): NetworkResult<T>(data= data)
    class Error<T>(message: String?, data: T? = null): NetworkResult<T>(message = message, data = data)
    class Loading<T>(): NetworkResult<T>()

}