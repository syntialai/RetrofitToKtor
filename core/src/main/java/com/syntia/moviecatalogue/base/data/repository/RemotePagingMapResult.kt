package com.syntia.moviecatalogue.base.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.syntia.moviecatalogue.base.data.source.paging.RemotePagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn

abstract class RemotePagingMapResult<Response : Any, Result : Any> :
    RemotePagingSource<Response, Result>() {

  private val pagingResult = Pager(
      config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
      pagingSourceFactory = { this })

  fun getResultFlow(dispatcher: CoroutineDispatcher) = pagingResult.flow.flowOn(dispatcher)
}