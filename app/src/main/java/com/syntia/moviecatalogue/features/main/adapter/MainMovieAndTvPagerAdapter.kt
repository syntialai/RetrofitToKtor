package com.syntia.moviecatalogue.features.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.syntia.moviecatalogue.features.home.view.HomeMovieFragment
import com.syntia.moviecatalogue.features.home.view.HomeTvShowsFragment
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MainMovieAndTvPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

  companion object {
    private const val MAIN_PAGER_COUNT = 2
    const val MAIN_MOVIE_FRAGMENT_INDEX = 0
    const val MAIN_TV_FRAGMENT_INDEX = 1
  }

  override fun getItemCount(): Int = MAIN_PAGER_COUNT

  override fun createFragment(position: Int): Fragment = when (position) {
    MAIN_MOVIE_FRAGMENT_INDEX -> HomeMovieFragment.newInstance()
    MAIN_TV_FRAGMENT_INDEX -> HomeTvShowsFragment.newInstance()
    else -> Fragment()
  }
}
