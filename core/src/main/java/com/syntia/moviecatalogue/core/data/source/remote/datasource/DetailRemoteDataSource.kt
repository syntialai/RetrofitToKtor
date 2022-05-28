package com.syntia.moviecatalogue.core.data.source.remote.datasource

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Detail
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.MediaCredits
import kotlinx.coroutines.flow.Flow

interface DetailRemoteDataSource {

  suspend fun getMovieDetails(id: Int): Flow<ResponseWrapper<Detail>>

  suspend fun getTvDetails(id: Int): Flow<ResponseWrapper<Detail>>

  suspend fun getMovieCredits(id: Int): Flow<ResponseWrapper<MediaCredits>>

  suspend fun getTvCredits(id: Int): Flow<ResponseWrapper<MediaCredits>>
}