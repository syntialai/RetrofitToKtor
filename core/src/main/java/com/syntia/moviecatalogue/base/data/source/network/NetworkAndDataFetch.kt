package com.syntia.moviecatalogue.base.data.source.network

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.base.domain.model.result.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

abstract class NetworkAndDataFetch<Response, Result> {

  private var result: Flow<ResultWrapper<Result>> = flow {
    emit(ResultWrapper.Loading)
    when (val response = fetchData().single()) {
      is ResponseWrapper.Success -> {
        saveFetchResult(response.data)
        emit(getMappedResult(response.data))
      }
      is ResponseWrapper.Error -> emit(ResultWrapper.Error(response.error?.message))
      is ResponseWrapper.NetworkError -> emit(ResultWrapper.NetworkError)
    }
  }

  private fun getMappedResult(data: Response): ResultWrapper<Result> = ResultWrapper.Success(
      mapResponse(data))

  protected abstract suspend fun fetchData(): Flow<ResponseWrapper<Response>>

  protected abstract fun mapResponse(data: Response): Result

  protected abstract suspend fun saveFetchResult(data: Response)

  fun asFlow(): Flow<ResultWrapper<Result>> = result
}