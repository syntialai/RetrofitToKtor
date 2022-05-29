package com.syntia.moviecatalogue.core.data.source.remote.service.impl

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.config.api.ApiPath
import com.syntia.moviecatalogue.core.data.source.remote.response.search.SearchResult
import com.syntia.moviecatalogue.core.data.source.remote.service.SearchService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SearchServiceImpl(private val httpClient: HttpClient): SearchService {

  override suspend fun searchByQuery(page: Int, query: String): ListItemResponse<SearchResult> {
    return httpClient.get(ApiPath.SEARCH_MULTI) {
      url {
        parameters.append(ApiPath.PAGE, page.toString())
        parameters.append(ApiPath.QUERY, query)
      }
    }.body()
  }
}