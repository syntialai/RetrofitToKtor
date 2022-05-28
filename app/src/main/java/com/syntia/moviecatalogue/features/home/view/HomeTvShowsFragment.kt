package com.syntia.moviecatalogue.features.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.syntia.moviecatalogue.R
import com.syntia.moviecatalogue.base.presentation.view.BaseFragment
import com.syntia.moviecatalogue.base.utils.DataMapper
import com.syntia.moviecatalogue.base.utils.showOrRemove
import com.syntia.moviecatalogue.databinding.FragmentHomeTvBinding
import com.syntia.moviecatalogue.features.home.adapter.HomeTvShowsAdapter
import com.syntia.moviecatalogue.features.home.viewmodel.HomeTvShowsViewModel
import com.syntia.moviecatalogue.features.utils.Router
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeTvShowsFragment : BaseFragment<FragmentHomeTvBinding, HomeTvShowsViewModel>() {

  companion object {
    fun newInstance() = HomeTvShowsFragment()
  }

  private val tvShowsAdapter by lazy {
    HomeTvShowsAdapter(::goToDetails)
  }

  override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeTvBinding
    get() = FragmentHomeTvBinding::inflate

  override val viewModel: HomeTvShowsViewModel by viewModel()

  override fun setupViews() {
    binding.recyclerViewHomeTv.apply {
      layoutManager = StaggeredGridLayoutManager(HomeMovieFragment.COLUMN_SPAN_COUNT,
          StaggeredGridLayoutManager.VERTICAL)
      adapter = tvShowsAdapter.withLoadStateHeaderAndFooter(header = loadStateAdapter,
          footer = loadStateAdapter)
      setHasFixedSize(false)
    }
  }

  override fun setupObserver() {
    viewModel.fetchTvShows()
    viewModel.tvShows.observe(viewLifecycleOwner, { data ->
      launchLifecycleScope {
        tvShowsAdapter.submitData(data)
      }
    })
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
      recyclerViewHomeTv.showOrRemove(isEmpty.not())
      groupTvEmptyState.showOrRemove(isEmpty)
    }
  }

  private fun goToDetails(id: Int) {
    Router.goToDetails(requireContext(), id, DataMapper.TV)
  }
}