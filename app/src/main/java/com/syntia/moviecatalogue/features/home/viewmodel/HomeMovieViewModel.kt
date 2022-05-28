package com.syntia.moviecatalogue.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.syntia.moviecatalogue.base.presentation.viewmodel.BaseViewModel
import com.syntia.moviecatalogue.core.domain.model.movie.MovieUiModel
import com.syntia.moviecatalogue.core.domain.repository.TrendingRepository
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class HomeMovieViewModel(private val trendingRepository: TrendingRepository) : BaseViewModel() {

  private var _movies = MutableLiveData<PagingData<MovieUiModel>>()
  val movies: LiveData<PagingData<MovieUiModel>>
    get() = _movies

  fun fetchMovies() {
    launchViewModelScope {
      trendingRepository.getPopularMovies().runPagingFlow {
        _movies.value = it
      }
    }
  }
}