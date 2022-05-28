package com.syntia.moviecatalogue.favorite.di

import com.syntia.moviecatalogue.favorite.presentation.viewmodel.FavoriteMovieViewModel
import com.syntia.moviecatalogue.favorite.presentation.viewmodel.FavoriteTvShowsViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@InternalCoroutinesApi
val favoriteViewModelModules = module {
  viewModel { FavoriteMovieViewModel(get()) }
  viewModel { FavoriteTvShowsViewModel(get()) }
}