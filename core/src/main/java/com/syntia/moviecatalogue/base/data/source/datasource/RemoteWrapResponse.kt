package com.syntia.moviecatalogue.base.data.source.datasource

import com.google.gson.Gson
import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.base.data.remote.response.error.ErrorResponse
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

abstract class RemoteWrapResponse<Response> {

  protected abstract suspend fun fetchData(): Response

  fun getResult(dispatcher: CoroutineDispatcher) = result.flowOn(dispatcher)

  private val result = flow {
    try {
      emit(ResponseWrapper.Success(fetchData()))
    } catch (throwable: Throwable) {
      emit(when (throwable) {
        is IOException -> ResponseWrapper.NetworkError
        is HttpException -> getErrorResponseWrapper(throwable)
        else -> ResponseWrapper.Error()
      })
    }
  }.catch { throwable ->
    when (throwable) {
      is IOException -> ResponseWrapper.NetworkError
      is HttpException -> getErrorResponseWrapper(throwable)
      else -> ResponseWrapper.Error()
    }
  }

  private fun getErrorResponseWrapper(exception: HttpException): ResponseWrapper.Error {
    return ResponseWrapper.Error(exception.code(), getErrorResponse(exception))
  }

  private fun getErrorResponse(exception: HttpException) = try {
    Gson().fromJson(exception.response()?.errorBody()?.charStream(), ErrorResponse::class.java)
  } catch (ex: Exception) {
    null
  }
}