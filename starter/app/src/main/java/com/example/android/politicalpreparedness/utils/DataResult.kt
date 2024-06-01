package com.example.android.politicalpreparedness.utils

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Failure(val message: String) : DataResult<Nothing>()

    val isSuccess: Boolean
        get() = this is Success

    val resultData:T?
        get() {
            if (this is Success) return this.data
            return null
        }
}