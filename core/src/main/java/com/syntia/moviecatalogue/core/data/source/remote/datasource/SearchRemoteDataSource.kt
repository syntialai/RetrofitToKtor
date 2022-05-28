package com.syntia.moviecatalogue.core.data.source.remote.datasource

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.data.source.remote.response.search.SearchResult

interface SearchRemoteDataSource {

  suspend fun searchByQuery(page: Int, query: String): ListItemResponse<SearchResult>
}