package com.syntia.moviecatalogue.di

import com.syntia.moviecatalogue.features.detail.viewmodel.DetailViewModel
import com.syntia.moviecatalogue.features.home.viewmodel.HomeMovieViewModel
import com.syntia.moviecatalogue.features.home.viewmodel.HomeTvShowsViewModel
import com.syntia.moviecatalogue.features.main.viewmodel.MainViewModel
import com.syntia.moviecatalogue.features.search.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val trendingFeatureModule = module {
  viewModel { MainViewModel(get()) }
  viewModel { HomeMovieViewModel(get()) }
  viewModel { HomeTvShowsViewModel(get()) }
}

val detailFeatureModule = module {
  viewModel { DetailViewModel(get(), get()) }
}

val searchFeatureModule = module {
  viewModel { SearchViewModel(get()) }
}