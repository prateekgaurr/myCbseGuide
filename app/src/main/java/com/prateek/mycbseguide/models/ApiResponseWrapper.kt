package com.prateek.mycbseguide.models

sealed class ApiResponseWrapper<T> (val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ApiResponseWrapper<T>(data)
    class KnownError<T>(message: String?, data: T? = null) : ApiResponseWrapper<T>(data, message)
    class UnknownError<T> : ApiResponseWrapper<T>()
    class Loading<T> : ApiResponseWrapper<T>()
    class Init<T> : ApiResponseWrapper<T>()
    class Timeout<T> : ApiResponseWrapper<T>()
    class Offline<T> : ApiResponseWrapper<T>()
}