package com.syntia.moviecatalogue.core.data.repository

import androidx.paging.PagingData
import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.base.data.repository.RemotePagingMapResult
import com.syntia.moviecatalogue.core.data.source.remote.datasource.SearchRemoteDataSource
import com.syntia.moviecatalogue.core.data.source.remote.response.search.SearchResult
import com.syntia.moviecatalogue.core.domain.model.search.SearchResultUiModel
import com.syntia.moviecatalogue.core.domain.repository.SearchRepository
import com.syntia.moviecatalogue.core.utils.mapper.SearchMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(private val searchRemoteDataSource: SearchRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher) : SearchRepository {

  override suspend fun searchByQuery(query: String): Flow<PagingData<SearchResultUiModel>> {
    return object : RemotePagingMapResult<SearchResult, SearchResultUiModel>() {
      override suspend fun fetchData(page: Int): ListItemResponse<SearchResult> {
        return searchRemoteDataSource.searchByQuery(page, query)
      }

      override suspend fun mapData(
          data: ListItemResponse<SearchResult>): List<SearchResultUiModel> {
        return SearchMapper.toSearchResultUiModels(data)
      }
    }.getResultFlow(ioDispatcher)
  }
}