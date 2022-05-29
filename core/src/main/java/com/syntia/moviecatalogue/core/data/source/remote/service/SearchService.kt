package com.syntia.moviecatalogue.core.data.source.remote.service

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.data.source.remote.response.search.SearchResult

interface SearchService {

  suspend fun searchByQuery(page: Int, query: String): ListItemResponse<SearchResult>
}