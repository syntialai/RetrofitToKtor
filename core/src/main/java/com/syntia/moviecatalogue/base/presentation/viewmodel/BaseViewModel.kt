package com.syntia.moviecatalogue.base.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.syntia.moviecatalogue.base.domain.model.result.ResultWrapper
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@InternalCoroutinesApi
abstract class BaseViewModel : ViewModel() {

  private val _resultWrapperStatus = MutableLiveData<ResultWrapper<*>>()
  val resultWrapperStatus: LiveData<ResultWrapper<*>>
    get() = _resultWrapperStatus

  protected fun launchViewModelScope(block: suspend () -> Unit) {
    viewModelScope.launch {
      block.invoke()
    }
  }

  protected suspend fun <T> Flow<ResultWrapper<T>>.runFlow(onSuccessFetch: (T) -> Unit) {
    collect {
      checkResponse(it, onSuccessFetch)
    }
  }

  protected suspend fun <T : Any> Flow<PagingData<T>>.runPagingFlow(
      onSuccessFetch: (PagingData<T>) -> Unit) {
    cachedIn(viewModelScope).collectLatest {
      onSuccessFetch.invoke(it)
    }
  }

  protected fun <T> checkResponse(result: ResultWrapper<T>, onSuccessFetch: (T) -> Unit,
      onFailFetch: (() -> Unit)? = null) {
    _resultWrapperStatus.value = result
    when (result) {
      is ResultWrapper.Success -> onSuccessFetch.invoke(result.data)
      is ResultWrapper.Loading -> {
      }
      else -> onFailFetch?.invoke()
    }
  }
}