package com.syntia.moviecatalogue.base.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.syntia.moviecatalogue.base.R
import com.syntia.moviecatalogue.base.domain.model.result.ResultWrapper
import com.syntia.moviecatalogue.base.presentation.viewmodel.BaseViewModel
import java.io.IOException
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
abstract class BaseActivity<VB : ViewBinding, out VM : BaseViewModel> :
    AppCompatActivity() {

  private var _binding: VB? = null
  protected val binding get() = _binding as VB

  protected abstract val viewBindingInflater: (LayoutInflater) -> VB

  protected abstract val viewModel: VM

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = viewBindingInflater.invoke(layoutInflater)
    setContentView(binding.root)
    setupViews()
    setupObserver()
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  abstract fun setupViews()

  open fun setupObserver() {
    viewModel.resultWrapperStatus.observe(this, { loadingState ->
      showLoadingState(loadingState is ResultWrapper.Loading)
      checkErrorState(loadingState)
    })
  }

  open fun showEmptyState(isEmpty: Boolean) {}

  open fun showLoadingState(isLoading: Boolean) {}

  open fun showErrorState(isError: Boolean, message: String?, @StringRes defaultMessageId: Int) {
    showErrorSnackbar((message ?: getString(defaultMessageId)))
  }

  private fun showErrorSnackbar(message: String) {
    if (message.isNotBlank()) {
      findViewById<View>(android.R.id.content)?.let { view ->
        getSnackbar(view, message).setBackgroundTint(
            getColor(android.R.color.holo_red_dark)).setTextColor(
            getColor(android.R.color.white)).show()
      }
    }
  }

  protected fun showSnackbar(message: String) {
    if (message.isNotBlank()) {
      findViewById<View>(android.R.id.content)?.let { view ->
        getSnackbar(view, message).show()
      }
    }
  }

  protected fun getDimenSize(@DimenRes dimenId: Int) = resources.getDimensionPixelSize(dimenId)

  private fun checkErrorState(resultWrapper: ResultWrapper<*>) {
    when (resultWrapper) {
      is ResultWrapper.Error -> showErrorState(true, resultWrapper.message,
          R.string.unknown_error_message)
      is ResultWrapper.NetworkError -> showErrorState(true, null, R.string.no_connection_message)
      else -> {
      }
    }
  }

  protected fun checkPagingErrorState(error: Throwable) {
    when (error) {
      is IOException -> showErrorState(true, null, R.string.no_connection_message)
      else -> showErrorState(true, error.message, R.string.unknown_error_message)
    }
  }

  private fun getSnackbar(view: View, message: String) = Snackbar.make(view, message,
      Snackbar.LENGTH_SHORT)
}