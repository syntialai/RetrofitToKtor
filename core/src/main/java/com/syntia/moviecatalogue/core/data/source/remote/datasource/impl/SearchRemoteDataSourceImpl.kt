package com.syntia.moviecatalogue.core.data.source.remote.datasource.impl

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.data.source.remote.datasource.SearchRemoteDataSource
import com.syntia.moviecatalogue.core.data.source.remote.response.search.SearchResult
import com.syntia.moviecatalogue.core.data.source.remote.service.SearchService

class SearchRemoteDataSourceImpl(private val searchService: SearchService) : SearchRemoteDataSource {

  override suspend fun searchByQuery(page: Int, query: String): ListItemResponse<SearchResult> {
    return searchService.searchByQuery(page, query)
  }
}