package com.syntia.moviecatalogue.features.home

import com.syntia.moviecatalogue.core.domain.model.movie.MovieUiModel
import com.syntia.moviecatalogue.core.domain.repository.TrendingRepository
import com.syntia.moviecatalogue.features.home.adapter.HomeMovieAdapter
import com.syntia.moviecatalogue.features.home.viewmodel.HomeMovieViewModel
import com.syntia.moviecatalogue.helper.BaseViewModelTest
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
class HomeMovieViewModelTest : BaseViewModelTest() {

  private lateinit var viewModel: HomeMovieViewModel

  private val trendingRepository = mock<TrendingRepository>()

  override fun setUp() {
    super.setUp()
    viewModel = HomeMovieViewModel(trendingRepository)
  }

  @Test
  fun `Given when fetch movies then update live data`() {
    val data = listOf(MovieUiModel(
        id = ID,
        title = TITLE,
        image = IMAGE,
        releasedYear = YEAR,
        voteAverage = VOTE_AVERAGE_STRING,
        adult = false
    ))
    val flow = data.getFakePagingData()
    val differ = getAsyncPagingDataDiffer(HomeMovieAdapter.diffCallback)

    rule.dispatcher.runBlockingTest {
      val job = launch {
        flow.collectLatest { data ->
          differ.submitData(data)
        }
      }

      whenever(trendingRepository.getPopularMovies()) doReturn flow

      viewModel.fetchMovies()
      verify(trendingRepository).getPopularMovies()
      advanceUntilIdle()

      assertTrue(differ.snapshot().contains(data[0]))
      Assert.assertNotNull(viewModel.movies.value)

      verifyNoMoreInteractions(trendingRepository)
      job.cancel()
    }
  }
}