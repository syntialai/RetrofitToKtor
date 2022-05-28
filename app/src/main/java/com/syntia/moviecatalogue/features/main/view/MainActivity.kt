package com.syntia.moviecatalogue.features.main.view

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import com.syntia.moviecatalogue.R
import com.syntia.moviecatalogue.base.presentation.view.BaseActivity
import com.syntia.moviecatalogue.base.utils.showOrRemove
import com.syntia.moviecatalogue.databinding.ActivityMainBinding
import com.syntia.moviecatalogue.features.main.adapter.MainMovieAndTvPagerAdapter
import com.syntia.moviecatalogue.features.main.adapter.MainTrendingItemAdapter
import com.syntia.moviecatalogue.features.main.viewmodel.MainViewModel
import com.syntia.moviecatalogue.features.utils.Router
import kotlin.math.abs
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

  private val trendingItemAdapter by lazy {
    MainTrendingItemAdapter(::goToDetails)
  }

  override val viewBindingInflater: (LayoutInflater) -> ActivityMainBinding
    get() = ActivityMainBinding::inflate

  override val viewModel: MainViewModel by viewModel()

  override fun setupViews() {
    setSupportActionBar(binding.layoutMainContent.toolbarMain)
    setupTrendingCarousel()
    setupTabLayoutAndViewPager()
  }

  override fun setupObserver() {
    super.setupObserver()

    viewModel.fetchTrendingItems()
    viewModel.trendingItems.observe(this, { trendingItems ->
      trendingItemAdapter.submitList(trendingItems)
      showEmptyState(trendingItems.isEmpty())
    })
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_toolbar_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_show_favorites -> Router.goToFavorites(this)
      R.id.menu_search -> Router.goToSearch(this)
    }
    return true
  }

  override fun showLoadingState(isLoading: Boolean) {
    binding.layoutMainContent.spinKitCarouselTrendingLoad.showOrRemove(isLoading)
  }

  override fun showEmptyState(isEmpty: Boolean) {
    binding.layoutMainContent.apply {
      textViewTrendingTitle.showOrRemove(isEmpty.not())
      viewPagerCarouselTrending.showOrRemove(isEmpty.not())
    }
  }

  private fun getCarouselPagerTransformer() = CompositePageTransformer().apply {
    addTransformer(MarginPageTransformer(resources.getDimensionPixelSize(R.dimen.dp_24)))
    addTransformer { page, position ->
      page.scaleY = 0.95f + (1 - abs(position)) * 0.05f
    }
  }

  private fun goToDetails(id: Int, type: String) {
    Router.goToDetails(this, id, type)
  }

  private fun setupTrendingCarousel() {
    with(binding.layoutMainContent.viewPagerCarouselTrending) {
      offscreenPageLimit = 3
      getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS
      setPageTransformer(getCarouselPagerTransformer())
      adapter = trendingItemAdapter
    }
  }

  private fun setupTabLayoutAndViewPager() {
    binding.apply {
      viewPagerMovieAndTvShows.adapter = MainMovieAndTvPagerAdapter(supportFragmentManager,
          lifecycle)
      TabLayoutMediator(tabLayoutMovieAndTvShows, viewPagerMovieAndTvShows) { tab, position ->
        tab.text = when (position) {
          MainMovieAndTvPagerAdapter.MAIN_MOVIE_FRAGMENT_INDEX -> getString(R.string.movie_label)
          MainMovieAndTvPagerAdapter.MAIN_TV_FRAGMENT_INDEX -> getString(R.string.tv_shows_label)
          else -> null
        }
      }.attach()
    }
  }
}