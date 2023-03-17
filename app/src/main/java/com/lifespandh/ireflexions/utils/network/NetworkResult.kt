package com.lifespandh.ireflexions.utils.network

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: String?) : NetworkResult<Nothing>()
}