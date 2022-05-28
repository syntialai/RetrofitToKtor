package com.syntia.moviecatalogue.core.data.source.remote.service

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.config.api.ApiPath
import com.syntia.moviecatalogue.core.data.source.remote.response.search.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

  @GET(ApiPath.SEARCH_MULTI)
  suspend fun searchByQuery(@Query(ApiPath.PAGE) page: Int,
      @Query(value = ApiPath.QUERY, encoded = true) query: String): ListItemResponse<SearchResult>
}