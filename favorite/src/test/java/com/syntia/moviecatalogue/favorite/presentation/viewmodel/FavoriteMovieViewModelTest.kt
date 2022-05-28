package com.syntia.moviecatalogue.favorite.presentation.viewmodel

import com.syntia.moviecatalogue.core.domain.model.movie.MovieUiModel
import com.syntia.moviecatalogue.core.domain.usecase.FavoriteItemUseCase
import com.syntia.moviecatalogue.favorite.helper.BaseViewModelTest
import com.syntia.moviecatalogue.favorite.presentation.adapter.FavoriteMovieAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FavoriteMovieViewModelTest : BaseViewModelTest() {

  private lateinit var viewModel: FavoriteMovieViewModel

  private val favoriteItemUseCase = mock<FavoriteItemUseCase>()

  override fun setUp() {
    super.setUp()
    viewModel = FavoriteMovieViewModel(favoriteItemUseCase)
  }

  @Test
  fun `Given when fetch favorite movies then update live data`() {
    val data = listOf(MovieUiModel(
        id = ID,
        title = TITLE,
        image = IMAGE,
        releasedYear = YEAR,
        voteAverage = VOTE_AVERAGE_STRING,
        adult = false
    ))
    val flow = data.getFakePagingData()
    val differ = getAsyncPagingDataDiffer(FavoriteMovieAdapter.diffCallback)

    rule.dispatcher.runBlockingTest {
      val job = launch {
        flow.collectLatest { data ->
          differ.submitData(data)
        }
      }

      whenever(favoriteItemUseCase.getFavoriteMovies()) doReturn flow

      viewModel.fetchFavoriteMovies()
      verify(favoriteItemUseCase).getFavoriteMovies()
      advanceUntilIdle()

      assertTrue(differ.snapshot().contains(data[0]))
      Assert.assertNotNull(viewModel.movies.value)

      verifyNoMoreInteractions(favoriteItemUseCase)
      job.cancel()
    }
  }
}