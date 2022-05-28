package com.syntia.moviecatalogue.core.data.source.remote.datasource

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.core.data.source.remote.datasource.impl.TrendingRemoteDataSourceImpl
import com.syntia.moviecatalogue.core.data.source.remote.response.movie.Movie
import com.syntia.moviecatalogue.core.data.source.remote.response.trending.TrendingItem
import com.syntia.moviecatalogue.core.data.source.remote.response.tvshow.TvShows
import com.syntia.moviecatalogue.core.data.source.remote.service.TrendingService
import com.syntia.moviecatalogue.core.helper.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class TrendingRemoteDataSourceImplTest : BaseTest() {

  private lateinit var trendingRemoteDataSource: TrendingRemoteDataSource

  @Mock
  private lateinit var trendingService: TrendingService

  override fun setUp() {
    super.setUp()
    trendingRemoteDataSource = TrendingRemoteDataSourceImpl(trendingService, dispatcher)
  }

  @Test
  fun `Given when getTrendingItems then return flow of response wrapper`() {
    val expected = generateListItemResponse(TrendingItem(
        firstAirDate = FIRST_AIR_DATE,
        id = ID,
        posterPath = POSTER_PATH,
        mediaType = MEDIA_TYPE,
        voteAverage = VOTE_AVERAGE,
        name = NAME,
        releaseDate = null,
        title = null
    ))

    dispatcher.runBlockingTest {
      trendingService.stub {
        onBlocking { getTrendingItems() } doReturn expected
      }

      val flow = trendingRemoteDataSource.getTrendingItems()

      flow.collectLatest { actual ->
        verify(trendingService).getTrendingItems()
        assertEquals(ResponseWrapper.Success(expected), actual)

        verifyNoMoreInteractions(trendingService)
      }
    }
  }

  @Test
  fun `Given when getPopularMovies then return result from service`() {
    val expected = generateListItemResponse(Movie(
        adult = true,
        backdropPath = null,
        id = ID,
        posterPath = POSTER_PATH,
        releaseDate = RELEASE_DATE,
        title = TITLE,
        voteAverage = VOTE_AVERAGE
    ))

    dispatcher.runBlockingTest {
      trendingService.stub {
        onBlocking { getPopularMovies(PAGE) } doReturn expected
      }

      val actual = trendingRemoteDataSource.getPopularMovies(PAGE)

      verify(trendingService).getPopularMovies(PAGE)
      assertEquals(expected, actual)

      verifyNoMoreInteractions(trendingService)
    }
  }

  @Test
  fun `Given when getPopularTvShows then return result from service`() {
    val expected = generateListItemResponse(TvShows(
        backdropPath = null,
        id = ID,
        posterPath = POSTER_PATH,
        firstAirDate = FIRST_AIR_DATE,
        name = NAME,
        voteAverage = VOTE_AVERAGE
    ))

    dispatcher.runBlockingTest {
      trendingService.stub {
        onBlocking { getPopularTvShows(PAGE) } doReturn expected
      }

      val actual = trendingRemoteDataSource.getPopularTvShows(PAGE)

      verify(trendingService).getPopularTvShows(PAGE)
      assertEquals(expected, actual)

      verifyNoMoreInteractions(trendingService)
    }
  }
}