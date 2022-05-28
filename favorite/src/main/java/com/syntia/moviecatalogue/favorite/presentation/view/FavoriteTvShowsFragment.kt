package com.syntia.moviecatalogue.favorite.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.syntia.moviecatalogue.R
import com.syntia.moviecatalogue.base.presentation.view.BaseFragment
import com.syntia.moviecatalogue.base.utils.DataMapper
import com.syntia.moviecatalogue.base.utils.showOrRemove
import com.syntia.moviecatalogue.favorite.databinding.FragmentFavoriteTvBinding
import com.syntia.moviecatalogue.favorite.presentation.adapter.FavoriteTvShowsAdapter
import com.syntia.moviecatalogue.favorite.presentation.viewmodel.FavoriteTvShowsViewModel
import com.syntia.moviecatalogue.features.utils.Router
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class FavoriteTvShowsFragment :
    BaseFragment<FragmentFavoriteTvBinding, FavoriteTvShowsViewModel>() {

  companion object {
    fun newInstance() = FavoriteTvShowsFragment()
  }

  private val tvShowsAdapter by lazy {
    FavoriteTvShowsAdapter(::goToDetails)
  }

  override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteTvBinding
    get() = FragmentFavoriteTvBinding::inflate

  override val viewModel: FavoriteTvShowsViewModel by viewModel()

  override fun setupViews() {
    binding.recyclerViewFavoriteTv.apply {
      layoutManager = StaggeredGridLayoutManager(FavoriteMovieFragment.COLUMN_SPAN_COUNT,
          StaggeredGridLayoutManager.VERTICAL)
      adapter = tvShowsAdapter.withLoadStateHeaderAndFooter(header = loadStateAdapter,
          footer = loadStateAdapter)
      setHasFixedSize(false)
    }
  }

  override fun setupObserver() {
    viewModel.tvShows.observe(viewLifecycleOwner, {
      launchLifecycleScope {
        tvShowsAdapter.submitData(it)
      }
    })
  }

  override fun onResume() {
    super.onResume()
    viewModel.fetchFavoriteTvShows()
  }

  override fun setupAdapterLoadStateListener() {
    tvShowsAdapter.addLoadStateListener { loadState ->
      when (loadState.refresh) {
        is LoadState.NotLoading -> showEmptyState(tvShowsAdapter.isEmpty())
        is LoadState.Error -> showErrorState((loadState.refresh as LoadState.Error).error.message,
            R.string.no_connection_message)
        else -> {
        }
      }
    }
  }

  override fun showEmptyState(isEmpty: Boolean) {
    super.showEmptyState(isEmpty)
    binding.apply {
      recyclerViewFavoriteTv.showOrRemove(isEmpty.not())
      groupTvEmptyState.showOrRemove(isEmpty)
    }
  }

  private fun goToDetails(id: Int) {
    Router.goToDetails(requireContext(), id, DataMapper.TV)
  }
}