package com.syntia.moviecatalogue.favorite.presentation.viewmodel

import com.syntia.moviecatalogue.core.domain.model.tvshow.TvShowsUiModel
import com.syntia.moviecatalogue.core.domain.usecase.FavoriteItemUseCase
import com.syntia.moviecatalogue.favorite.helper.BaseViewModelTest
import com.syntia.moviecatalogue.favorite.presentation.adapter.FavoriteTvShowsAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FavoriteTvShowsViewModelTest : BaseViewModelTest() {

  private lateinit var viewModel: FavoriteTvShowsViewModel

  private val favoriteItemUseCase = mock<FavoriteItemUseCase>()

  override fun setUp() {
    super.setUp()
    viewModel = FavoriteTvShowsViewModel(favoriteItemUseCase)
  }

  @Test
  fun `Given when fetch favorite tv shows then success update live data`() {
    val data = listOf(TvShowsUiModel(
        id = ID,
        image = IMAGE,
        releasedYear = YEAR,
        title = NAME,
        voteAverage = VOTE_AVERAGE_STRING
    ))
    val flow = data.getFakePagingData()
    val differ = getAsyncPagingDataDiffer(FavoriteTvShowsAdapter.diffCallback)

    rule.dispatcher.runBlockingTest {
      val job = launch {
        flow.collectLatest { data ->
          differ.submitData(data)
        }
      }

      whenever(favoriteItemUseCase.getFavoriteTvShows()) doReturn flow

      viewModel.fetchFavoriteTvShows()
      verify(favoriteItemUseCase).getFavoriteTvShows()
      advanceUntilIdle()

      Assert.assertTrue(differ.snapshot().contains(data[0]))
      Assert.assertNotNull(viewModel.tvShows.value)

      verifyNoMoreInteractions(favoriteItemUseCase)
      job.cancel()
    }
  }
}