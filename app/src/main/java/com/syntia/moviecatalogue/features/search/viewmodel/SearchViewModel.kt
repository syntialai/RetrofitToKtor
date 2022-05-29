package com.syntia.moviecatalogue.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.syntia.moviecatalogue.base.presentation.viewmodel.BaseViewModel
import com.syntia.moviecatalogue.core.domain.model.search.SearchResultUiModel
import com.syntia.moviecatalogue.core.domain.repository.SearchRepository

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {

  private var _searchResults = MutableLiveData<PagingData<SearchResultUiModel>>()
  val searchResult: LiveData<PagingData<SearchResultUiModel>>
    get() = _searchResults

  fun searchQuery(query: String) {
    launchViewModelScope {
      searchRepository.searchByQuery(query).runPagingFlow {
        _searchResults.value = it
      }
    }
  }
}