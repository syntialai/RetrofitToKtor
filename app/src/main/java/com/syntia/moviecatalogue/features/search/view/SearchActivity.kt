package com.syntia.moviecatalogue.features.search.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.syntia.moviecatalogue.R
import com.syntia.moviecatalogue.base.presentation.adapter.PagingLoadStateAdapter
import com.syntia.moviecatalogue.base.presentation.view.BaseActivity
import com.syntia.moviecatalogue.base.utils.showOrRemove
import com.syntia.moviecatalogue.databinding.ActivitySearchBinding
import com.syntia.moviecatalogue.features.search.adapter.SearchResultAdapter
import com.syntia.moviecatalogue.features.search.viewmodel.SearchViewModel
import com.syntia.moviecatalogue.features.utils.Router
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(),
    View.OnClickListener {

  companion object {
    const val COLUMN_SPAN_COUNT = 2
  }

  override val viewBindingInflater: (LayoutInflater) -> ActivitySearchBinding
    get() = ActivitySearchBinding::inflate

  override val viewModel: SearchViewModel by viewModel()

  private val loadStateAdapter by lazy {
    PagingLoadStateAdapter(::checkPagingErrorState)
  }

  private val searchAdapter by lazy {
    SearchResultAdapter(::goToDetails)
  }

  private var searchJob: Job? = null

  @RequiresApi(Build.VERSION_CODES.O)
  override fun setupViews() {
    setupSearchView()
    setupRecyclerView()
    setupAdapterLoadStateListener()
    binding.apply {
      buttonBackFromSearch.setOnClickListener(this@SearchActivity)
      searchView.isFocusedByDefault = true
    }
  }

  override fun setupObserver() {
    super.setupObserver()

    viewModel.searchResult.observe(this, {
      launchSearchJob {
        searchAdapter.submitData(it)
      }
    })
  }

  override fun onClick(view: View?) {
    with(binding) {
      when (view) {
        buttonBackFromSearch -> onBackPressed()
      }
    }
  }

  override fun showEmptyState(isEmpty: Boolean) {
    super.showEmptyState(isEmpty)
    binding.apply {
      recyclerViewSearch.showOrRemove(isEmpty.not())
      groupSearchEmptyState.showOrRemove(isEmpty)
    }
  }

  override fun showErrorState(isError: Boolean, message: String?,
      @StringRes defaultMessageId: Int) {
    super.showErrorState(isError, message, defaultMessageId)
    binding.apply {
      recyclerViewSearch.showOrRemove(isError.not())
      groupSearchEmptyState.showOrRemove(isError.not())
    }
  }

  private fun goToDetails(id: Int, type: String) {
    Router.goToDetails(this, id, type)
  }

  private fun launchSearchJob(block: suspend () -> Unit) {
    searchJob?.cancel()
    searchJob = lifecycleScope.launch {
      block.invoke()
    }
  }

  private fun setupAdapterLoadStateListener() {
    searchAdapter.addLoadStateListener { loadState ->
      when (loadState.refresh) {
        is LoadState.NotLoading -> showEmptyState(searchAdapter.isEmpty())
        is LoadState.Error -> showErrorState(true,
            (loadState.refresh as LoadState.Error).error.message, R.string.no_connection_message)
        else -> {
        }
      }
    }
  }

  private fun setupSearchView() {
    binding.searchView.apply {
      setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
          query?.trim()?.let {
            viewModel.searchQuery(it)
          }
          return true
        }

        override fun onQueryTextChange(newText: String?): Boolean = false
      })
    }
  }

  private fun setupRecyclerView() {
    binding.recyclerViewSearch.apply {
      layoutManager = StaggeredGridLayoutManager(COLUMN_SPAN_COUNT,
          StaggeredGridLayoutManager.VERTICAL)
      adapter = searchAdapter.withLoadStateHeaderAndFooter(header = loadStateAdapter,
          footer = loadStateAdapter)
      setHasFixedSize(false)
    }
  }
}