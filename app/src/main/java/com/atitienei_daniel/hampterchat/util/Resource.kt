package com.atitienei_daniel.hampterchat.util

sealed class Resource<T>(val data: T? = null, val error: Exception? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
    class Error<T>(error: Exception?, data: T? = null) : Resource<T>(data, error)
}