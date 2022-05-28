package com.syntia.moviecatalogue.base.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.syntia.moviecatalogue.base.data.source.paging.LocalPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn

abstract class LocalPagingMapResult<Response : Any, Result : Any> :
    LocalPagingSource<Response, Result>() {

  private val pagingResult = Pager(
      config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
      pagingSourceFactory = { this })

  fun getResultFlow(dispatcher: CoroutineDispatcher) = pagingResult.flow.flowOn(dispatcher)
}