package com.syntia.moviecatalogue.base.domain.model.result

sealed class ResultWrapper<out T> {

  data class Success<T>(val data: T) : ResultWrapper<T>()

  data class Error(val message: String? = null) : ResultWrapper<Nothing>()

  object Loading : ResultWrapper<Nothing>()

  object NetworkError : ResultWrapper<Nothing>()
}