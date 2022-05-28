package com.syntia.moviecatalogue.base.data.source.paging

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse

abstract class RemotePagingSource<T : Any, U : Any> : BasePagingSource<T, U>() {

  override val initialPage: Int = 1

  override suspend fun getResult(page: Int): List<U> {
    return mapData(fetchData(page))
  }

  protected abstract suspend fun fetchData(page: Int): ListItemResponse<T>

  protected abstract suspend fun mapData(data: ListItemResponse<T>): List<U>
}