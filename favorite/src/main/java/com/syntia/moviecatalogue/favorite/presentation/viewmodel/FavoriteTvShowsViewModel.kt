package com.syntia.moviecatalogue.favorite.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.syntia.moviecatalogue.base.presentation.viewmodel.BaseViewModel
import com.syntia.moviecatalogue.core.domain.model.tvshow.TvShowsUiModel
import com.syntia.moviecatalogue.core.domain.repository.FavoriteItemRepository

class FavoriteTvShowsViewModel(private val favoriteItemRepository: FavoriteItemRepository) :
    BaseViewModel() {

  private var _tvShows = MutableLiveData<PagingData<TvShowsUiModel>>()
  val tvShows: LiveData<PagingData<TvShowsUiModel>>
    get() = _tvShows

  fun fetchFavoriteTvShows() {
    launchViewModelScope {
      favoriteItemRepository.getFavoriteTvShows().runPagingFlow {
        _tvShows.value = it
      }
    }
  }
}