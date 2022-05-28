package com.syntia.moviecatalogue.di

import com.syntia.moviecatalogue.features.detail.viewmodel.DetailViewModel
import com.syntia.moviecatalogue.features.home.viewmodel.HomeMovieViewModel
import com.syntia.moviecatalogue.features.home.viewmodel.HomeTvShowsViewModel
import com.syntia.moviecatalogue.features.main.viewmodel.MainViewModel
import com.syntia.moviecatalogue.features.search.viewmodel.SearchViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@InternalCoroutinesApi
val trendingFeatureModule = module {
  viewModel { MainViewModel(get()) }
  viewModel { HomeMovieViewModel(get()) }
  viewModel { HomeTvShowsViewModel(get()) }
}

@InternalCoroutinesApi
val detailFeatureModule = module {
  viewModel { DetailViewModel(get(), get()) }
}

@InternalCoroutinesApi
val searchFeatureModule = module {
  viewModel { SearchViewModel(get()) }
}