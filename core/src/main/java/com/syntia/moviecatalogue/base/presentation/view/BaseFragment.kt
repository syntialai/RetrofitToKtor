package com.syntia.moviecatalogue.base.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.syntia.moviecatalogue.base.R
import com.syntia.moviecatalogue.base.domain.model.result.ResultWrapper
import com.syntia.moviecatalogue.base.presentation.adapter.PagingLoadStateAdapter
import com.syntia.moviecatalogue.base.presentation.viewmodel.BaseViewModel
import java.io.IOException
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@InternalCoroutinesApi
abstract class BaseFragment<VB : ViewBinding, out VM : BaseViewModel> : Fragment() {

  private var _binding: VB? = null
  protected val binding get() = _binding as VB

  private var lifecycleJob: Job? = null

  protected abstract val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

  protected abstract val viewModel: VM

  protected val loadStateAdapter by lazy {
    PagingLoadStateAdapter(::checkPagingErrorState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    _binding = viewBindingInflater.invoke(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViews()
    setupAdapterLoadStateListener()
    setupObserver()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  abstract fun setupViews()

  abstract fun setupObserver()

  open fun setupAdapterLoadStateListener() {}

  open fun showEmptyState(isEmpty: Boolean) {}

  protected fun showErrorState(message: String?, @StringRes defaultMessageId: Int) {
    showErrorSnackbar((message ?: getString(defaultMessageId)))
  }

  private fun showErrorSnackbar(message: String) {
    if (message.isNotBlank()) {
      view?.findViewById<View>(android.R.id.content)?.let { view ->
        getSnackbar(view, message).setBackgroundTint(
            getColor(requireContext(), android.R.color.holo_red_dark)).setTextColor(
            getColor(requireContext(), android.R.color.white)).show()
      }
    }
  }

  protected fun showSnackbar(message: String) {
    if (message.isNotBlank()) {
      view?.findViewById<View>(android.R.id.content)?.let { view ->
        getSnackbar(view, message).show()
      }
    }
  }

  private fun checkErrorState(resultWrapper: ResultWrapper<*>) {
    when (resultWrapper) {
      is ResultWrapper.Error -> showErrorState(resultWrapper.message,
          R.string.unknown_error_message)
      is ResultWrapper.NetworkError -> showErrorState(null, R.string.no_connection_message)
      else -> {
      }
    }
  }

  private fun checkPagingErrorState(error: Throwable) {
    when (error) {
      is IOException -> showErrorState(null, R.string.no_connection_message)
      else -> showErrorState(error.message, R.string.unknown_error_message)
    }
  }

  private fun getSnackbar(view: View, message: String) = Snackbar.make(view, message,
      Snackbar.LENGTH_SHORT)

  protected fun launchLifecycleScope(block: suspend () -> Unit) {
    lifecycleJob?.cancel()
    lifecycleJob = lifecycleScope.launch {
      block.invoke()
    }
  }
}