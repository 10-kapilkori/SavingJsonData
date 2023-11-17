package com.test.savingjsondata.utils

enum class Status { SUCCESS, FAILURE, LOADING }

// A resource class for checking the status of the API
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> failure(msg: String, data: T?): Resource<T> {
            return Resource(Status.FAILURE, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
