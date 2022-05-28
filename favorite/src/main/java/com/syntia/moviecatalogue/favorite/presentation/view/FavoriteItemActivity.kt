package com.syntia.moviecatalogue.favorite.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.syntia.moviecatalogue.R
import com.syntia.moviecatalogue.favorite.databinding.ActivityFavoriteItemBinding
import com.syntia.moviecatalogue.favorite.di.favoriteViewModelModules
import com.syntia.moviecatalogue.favorite.presentation.adapter.FavoriteItemPagerAdapter
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.core.context.loadKoinModules

@InternalCoroutinesApi
class FavoriteItemActivity : AppCompatActivity() {

  private lateinit var binding: ActivityFavoriteItemBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFavoriteItemBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupToolbar()
    setupTabLayoutAndViewPager()
    loadKoinModules(favoriteViewModelModules)
  }

  private fun setupTabLayoutAndViewPager() {
    binding.apply {
      viewPagerFavoriteItems.adapter = FavoriteItemPagerAdapter(supportFragmentManager, lifecycle)
      TabLayoutMediator(tabLayoutFavoriteItem, viewPagerFavoriteItems) { tab, position ->
        tab.text = when (position) {
          FavoriteItemPagerAdapter.FAVORITE_MOVIE_FRAGMENT_INDEX -> getString(R.string.movie_label)
          FavoriteItemPagerAdapter.FAVORITE_TV_FRAGMENT_INDEX -> getString(R.string.tv_shows_label)
          else -> null
        }
      }.attach()
    }
  }

  private fun setupToolbar() {
    binding.toolbarFavoriteItem.apply {
      setSupportActionBar(this)
      setNavigationOnClickListener {
        onBackPressed()
      }
    }
  }
}