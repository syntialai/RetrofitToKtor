package com.syntia.moviecatalogue.favorite.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.syntia.moviecatalogue.R
import com.syntia.moviecatalogue.base.presentation.view.BaseFragment
import com.syntia.moviecatalogue.base.utils.DataMapper
import com.syntia.moviecatalogue.base.utils.showOrRemove
import com.syntia.moviecatalogue.favorite.databinding.FragmentFavoriteMovieBinding
import com.syntia.moviecatalogue.favorite.presentation.adapter.FavoriteMovieAdapter
import com.syntia.moviecatalogue.favorite.presentation.viewmodel.FavoriteMovieViewModel
import com.syntia.moviecatalogue.features.utils.Router
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class FavoriteMovieFragment : BaseFragment<FragmentFavoriteMovieBinding, FavoriteMovieViewModel>() {

  companion object {
    const val COLUMN_SPAN_COUNT = 2

    fun newInstance() = FavoriteMovieFragment()
  }

  private val movieAdapter by lazy {
    FavoriteMovieAdapter(::goToDetails)
  }

  override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteMovieBinding
    get() = FragmentFavoriteMovieBinding::inflate

  override val viewModel: FavoriteMovieViewModel by viewModel()

  override fun setupViews() {
    binding.recyclerViewFavoriteMovie.apply {
      layoutManager = StaggeredGridLayoutManager(COLUMN_SPAN_COUNT,
          StaggeredGridLayoutManager.VERTICAL)
      adapter = movieAdapter.withLoadStateHeaderAndFooter(header = loadStateAdapter,
          footer = loadStateAdapter)
      setHasFixedSize(false)
    }
  }

  override fun setupObserver() {
    viewModel.movies.observe(viewLifecycleOwner, {
      launchLifecycleScope {
        movieAdapter.submitData(it)
      }
    })
  }

  override fun onResume() {
    super.onResume()
    viewModel.fetchFavoriteMovies()
  }

  override fun setupAdapterLoadStateListener() {
    movieAdapter.addLoadStateListener { loadState ->
      when (loadState.refresh) {
        is LoadState.NotLoading -> showEmptyState(movieAdapter.isEmpty())
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
      recyclerViewFavoriteMovie.showOrRemove(isEmpty.not())
      groupMovieEmptyState.showOrRemove(isEmpty)
    }
  }

  private fun goToDetails(id: Int) {
    Router.goToDetails(requireContext(), id, DataMapper.MOVIE)
  }
}