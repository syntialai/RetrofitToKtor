package com.syntia.moviecatalogue.core.domain.repository

import androidx.paging.PagingData
import com.syntia.moviecatalogue.core.domain.model.search.SearchResultUiModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

  suspend fun searchByQuery(query: String): Flow<PagingData<SearchResultUiModel>>
}