package com.syntia.moviecatalogue.core.data.repository

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.core.data.source.remote.datasource.TrendingRemoteDataSource
import com.syntia.moviecatalogue.core.data.source.remote.response.trending.TrendingItem
import com.syntia.moviecatalogue.core.domain.repository.TrendingRepository
import com.syntia.moviecatalogue.core.helper.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class TrendingRepositoryImplTest : BaseTest() {

  private lateinit var trendingRepository: TrendingRepository

  @Mock
  private lateinit var trendingRemoteDataSource: TrendingRemoteDataSource

  override fun setUp() {
    super.setUp()
    trendingRepository = TrendingRepositoryImpl(trendingRemoteDataSource, dispatcher)
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
      trendingRemoteDataSource.stub {
        onBlocking { getTrendingItems() } doReturn getFlow(ResponseWrapper.Success(expected), true)
      }

      val flow = trendingRepository.getTrendingItems()

      flow.collectLatest {
        verify(trendingRemoteDataSource).getTrendingItems()
        verifyNoMoreInteractions(trendingRemoteDataSource)
      }
    }
  }
}