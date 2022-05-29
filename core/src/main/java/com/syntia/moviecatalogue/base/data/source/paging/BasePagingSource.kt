package com.syntia.moviecatalogue.base.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException

abstract class BasePagingSource<in T : Any, U : Any> : PagingSource<Int, U>() {

  companion object {
    private const val INCREMENT_PAGE = 1
    const val PAGE_SIZE = 20
  }

  protected abstract val initialPage: Int

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, U> {
    val pageIndex = params.key ?: initialPage
    return try {
      val responseData = getResult(pageIndex)
      LoadResult.Page(
          data = responseData,
          prevKey = if (pageIndex == initialPage) null else pageIndex - 1,
          nextKey = if (responseData.isEmpty()) null else pageIndex + 1
      )
    } catch (ioException: IOException) {
      return LoadResult.Error(ioException)
    } catch (clientRequestException: ClientRequestException) {
      return LoadResult.Error(clientRequestException)
    } catch (serverResponseException: ServerResponseException) {
      return LoadResult.Error(serverResponseException)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, U>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(INCREMENT_PAGE)
      ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(INCREMENT_PAGE)
    }
  }

  abstract suspend fun getResult(page: Int): List<U>

  private fun getNextKey(isResultEmpty: Boolean, pageIndex: Int, loadSize: Int) = if (isResultEmpty) {
    null
  } else {
    pageIndex + loadSize / PAGE_SIZE
  }
}