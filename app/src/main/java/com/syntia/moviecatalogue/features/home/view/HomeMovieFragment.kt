package com.syntia.moviecatalogue.features.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.syntia.moviecatalogue.R
import com.syntia.moviecatalogue.base.presentation.view.BaseFragment
import com.syntia.moviecatalogue.base.utils.DataMapper
import com.syntia.moviecatalogue.base.utils.showOrRemove
import com.syntia.moviecatalogue.databinding.FragmentHomeMovieBinding
import com.syntia.moviecatalogue.features.home.adapter.HomeMovieAdapter
import com.syntia.moviecatalogue.features.home.viewmodel.HomeMovieViewModel
import com.syntia.moviecatalogue.features.utils.Router
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeMovieFragment : BaseFragment<FragmentHomeMovieBinding, HomeMovieViewModel>() {

  companion object {
    const val COLUMN_SPAN_COUNT = 2

    fun newInstance() = HomeMovieFragment()
  }

  private val movieAdapter by lazy {
    HomeMovieAdapter(::goToDetails)
  }

  override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeMovieBinding
    get() = FragmentHomeMovieBinding::inflate

  override val viewModel: HomeMovieViewModel by viewModel()

  override fun setupViews() {
    binding.recyclerViewHomeMovie.apply {
      layoutManager = StaggeredGridLayoutManager(COLUMN_SPAN_COUNT,
          StaggeredGridLayoutManager.VERTICAL)
      adapter = movieAdapter.withLoadStateHeaderAndFooter(header = loadStateAdapter,
          footer = loadStateAdapter)
      setHasFixedSize(false)
    }
  }

  override fun setupObserver() {
    viewModel.fetchMovies()
    viewModel.movies.observe(viewLifecycleOwner, {
      launchLifecycleScope {
        movieAdapter.submitData(it)
      }
    })
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
      recyclerViewHomeMovie.showOrRemove(isEmpty.not())
      groupMovieEmptyState.showOrRemove(isEmpty)
    }
  }

  private fun goToDetails(id: Int) {
    Router.goToDetails(requireContext(), id, DataMapper.MOVIE)
  }
}