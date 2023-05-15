package com.aks.currentlocationdetector.data.repository

sealed class NetworkResources<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : NetworkResources<T>(data)
    class Error<T>(message: String?, data: T? = null) : NetworkResources<T>(data, message)
    class Loading<T>(data: T? = null) : NetworkResources<T>(data)
}