package com.syntia.moviecatalogue.base.data.repository

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.base.domain.model.result.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform

abstract class RemoteMapResult<Response, Result> {

  protected abstract suspend fun fetchData(): Flow<ResponseWrapper<Response>>

  protected abstract suspend fun mapData(data: Response): Result

  suspend fun getResult(dispatcher: CoroutineDispatcher): Flow<ResultWrapper<Result>> {
    return fetchData().transform { value ->
      when (value) {
        is ResponseWrapper.Error -> emit(ResultWrapper.Error(value.error?.message))
        is ResponseWrapper.NetworkError -> emit(ResultWrapper.NetworkError)
        is ResponseWrapper.Success -> emit(ResultWrapper.Success(mapData(value.data)))
      }
    }.onStart {
      emit(ResultWrapper.Loading)
    }.flowOn(dispatcher)
  }
}