package com.syntia.moviecatalogue.favorite.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.syntia.moviecatalogue.favorite.presentation.view.FavoriteMovieFragment
import com.syntia.moviecatalogue.favorite.presentation.view.FavoriteTvShowsFragment
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FavoriteItemPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

  companion object {
    private const val FAVORITE_ITEM_PAGER_COUNT = 2
    const val FAVORITE_MOVIE_FRAGMENT_INDEX = 0
    const val FAVORITE_TV_FRAGMENT_INDEX = 1
  }

  override fun getItemCount(): Int = FAVORITE_ITEM_PAGER_COUNT

  override fun createFragment(position: Int): Fragment = when (position) {
    FAVORITE_MOVIE_FRAGMENT_INDEX -> FavoriteMovieFragment.newInstance()
    FAVORITE_TV_FRAGMENT_INDEX -> FavoriteTvShowsFragment.newInstance()
    else -> Fragment()
  }
}