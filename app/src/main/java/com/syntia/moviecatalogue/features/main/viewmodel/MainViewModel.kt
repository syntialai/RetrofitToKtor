package com.syntia.moviecatalogue.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syntia.moviecatalogue.base.presentation.viewmodel.BaseViewModel
import com.syntia.moviecatalogue.core.domain.model.trending.TrendingItemUiModel
import com.syntia.moviecatalogue.core.domain.repository.TrendingRepository

class MainViewModel(private val trendingRepository: TrendingRepository) : BaseViewModel() {

  private var _trendingItems = MutableLiveData<List<TrendingItemUiModel>>()
  val trendingItems: LiveData<List<TrendingItemUiModel>>
    get() = _trendingItems

  fun fetchTrendingItems() {
    launchViewModelScope {
      trendingRepository.getTrendingItems().runFlow {
        _trendingItems.value = it
      }
    }
  }
}