package com.syntia.moviecatalogue.base.data.remote.response

import com.syntia.moviecatalogue.base.data.remote.response.error.ErrorResponse

sealed class ResponseWrapper<out T> {

  data class Success<out T>(val data: T) : ResponseWrapper<T>()

  data class Error(val code: Int? = null, val error: ErrorResponse? = null) : ResponseWrapper<Nothing>()

  object NetworkError : ResponseWrapper<Nothing>()
}
