package com.syntia.moviecatalogue.favorite.di

import com.syntia.moviecatalogue.favorite.presentation.viewmodel.FavoriteMovieViewModel
import com.syntia.moviecatalogue.favorite.presentation.viewmodel.FavoriteTvShowsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteViewModelModules = module {
  viewModel { FavoriteMovieViewModel(get()) }
  viewModel { FavoriteTvShowsViewModel(get()) }
}