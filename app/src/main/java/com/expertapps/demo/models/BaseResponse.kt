package com.expertapps.demo.models

sealed class BaseResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : BaseResponse<T>()
    data class Loading(val loading: Boolean) : BaseResponse<Nothing>()
    object None : BaseResponse<Nothing>()
}