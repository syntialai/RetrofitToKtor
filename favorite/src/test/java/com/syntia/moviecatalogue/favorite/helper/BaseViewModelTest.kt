package com.syntia.moviecatalogue.favorite.helper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.syntia.moviecatalogue.base.data.source.paging.BasePagingSource
import com.syntia.moviecatalogue.base.presentation.adapter.BaseDiffCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.junit.Rule

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
abstract class BaseViewModelTest : BaseTest() {

  @get:Rule
  val rule = CoroutineTestRule(dispatcher)

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  private val listUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
  }

  fun <T> LiveData<T>.observeForTesting(block: (actual: T?) -> Unit) {
    val observer = Observer<T> {}
    try {
      observeForever(observer)
      block(value)
    } finally {
      removeObserver(observer)
    }
  }

  protected fun <T : Any> List<T>.getFakePagingData(): Flow<PagingData<T>> {
    return Pager(
        config = PagingConfig(pageSize = BasePagingSource.PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { getFakePagingSource(this) }).flow.flowOn(rule.dispatcher)
  }

  protected fun <T : Any> getAsyncPagingDataDiffer(
      diffCallback: BaseDiffCallback<T>) = AsyncPagingDataDiffer(
      diffCallback = diffCallback,
      updateCallback = listUpdateCallback,
      mainDispatcher = dispatcher,
      workerDispatcher = dispatcher,
  )

  protected fun <T : Any> T.setPrivateField(variableName: String, data: Any) {
    return javaClass.getDeclaredField(variableName).let { field ->
      field.isAccessible = true
      field.set(this, data)
    }
  }

  private fun <T : Any> getFakePagingSource(data: List<T>): PagingSource<Int, T> {
    return object : PagingSource<Int, T>() {
      override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = null,
        )
      }

      override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
    }
  }
}