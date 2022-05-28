package com.syntia.moviecatalogue.core.data.source.remote.datasource.impl

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.base.data.source.datasource.RemoteWrapResponse
import com.syntia.moviecatalogue.core.data.source.remote.datasource.DetailRemoteDataSource
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Detail
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.MediaCredits
import com.syntia.moviecatalogue.core.data.source.remote.service.DetailService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class DetailRemoteDataSourceImpl(private val detailService: DetailService,
    private val ioDispatcher: CoroutineDispatcher) : DetailRemoteDataSource {

  override suspend fun getMovieDetails(id: Int): Flow<ResponseWrapper<Detail>> {
    return object : RemoteWrapResponse<Detail>() {
      override suspend fun fetchData(): Detail {
        return detailService.getMovieDetails(id)
      }
    }.getResult(ioDispatcher)
  }

  override suspend fun getTvDetails(id: Int): Flow<ResponseWrapper<Detail>> {
    return object : RemoteWrapResponse<Detail>() {
      override suspend fun fetchData(): Detail {
        return detailService.getTvDetails(id)
      }
    }.getResult(ioDispatcher)
  }

  override suspend fun getMovieCredits(id: Int): Flow<ResponseWrapper<MediaCredits>> {
    return object : RemoteWrapResponse<MediaCredits>() {
      override suspend fun fetchData(): MediaCredits {
        return detailService.getMovieCredits(id)
      }
    }.getResult(ioDispatcher)
  }

  override suspend fun getTvCredits(id: Int): Flow<ResponseWrapper<MediaCredits>> {
    return object : RemoteWrapResponse<MediaCredits>() {
      override suspend fun fetchData(): MediaCredits {
        return detailService.getTvCredits(id)
      }
    }.getResult(ioDispatcher)
  }
}