package com.syntia.moviecatalogue.base.data.source.datasource

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class RemoteWrapResponse<Response> {

  protected abstract suspend fun fetchData(): Response

  fun getResult(dispatcher: CoroutineDispatcher) = result.flowOn(dispatcher)

  private val result = flow {
    try {
      emit(ResponseWrapper.Success(fetchData()))
    } catch (throwable: Throwable) {
      emit(when (throwable) {
        is IOException -> ResponseWrapper.NetworkError
        is ClientRequestException -> getErrorResponseWrapper(throwable)
        else -> ResponseWrapper.Error()
      })
    }
  }.catch { throwable ->
    when (throwable) {
      is IOException -> ResponseWrapper.NetworkError
      is ClientRequestException -> getErrorResponseWrapper(throwable)
      else -> ResponseWrapper.Error()
    }
  }

  private suspend fun getErrorResponseWrapper(
      exception: ClientRequestException): ResponseWrapper.Error {
    return ResponseWrapper.Error(
        exception.response.status.value,
        exception.response.body())
  }
}