package com.syntia.moviecatalogue.base.data.source.paging

abstract class LocalPagingSource<T : Any, U : Any> : BasePagingSource<T, U>() {

  override val initialPage: Int = 0

  override suspend fun getResult(page: Int): List<U> {
    return mapData(fetchData(page, PAGE_SIZE))
  }

  protected abstract suspend fun fetchData(page: Int, pageSize: Int): List<T>

  protected abstract suspend fun mapData(data: List<T>): List<U>
}
