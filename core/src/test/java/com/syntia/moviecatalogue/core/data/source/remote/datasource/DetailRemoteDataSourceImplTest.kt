package com.syntia.moviecatalogue.core.data.source.remote.datasource

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.core.data.source.remote.datasource.impl.DetailRemoteDataSourceImpl
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Cast
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Detail
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.MediaCredits
import com.syntia.moviecatalogue.core.data.source.remote.service.DetailService
import com.syntia.moviecatalogue.core.helper.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DetailRemoteDataSourceImplTest : BaseTest() {

  private lateinit var detailRemoteDataSource: DetailRemoteDataSource

  @Mock
  private lateinit var detailService: DetailService

  override fun setUp() {
    super.setUp()
    detailRemoteDataSource = DetailRemoteDataSourceImpl(detailService, dispatcher)
  }

  @Test
  fun `Given when getMovieDetails then return flow of response wrapper`() {
    val expected = Detail(
        id = ID,
        posterPath = POSTER_PATH,
        title = TITLE,
        releaseDate = RELEASE_DATE,
        overview = null,
        voteAverage = VOTE_AVERAGE,
        genres = GENRES,
        originalLanguage = LANGUAGE
    )

    dispatcher.runBlockingTest {
      detailService.stub {
        onBlocking { getMovieDetails(ID) } doReturn expected
      }

      val flow = detailRemoteDataSource.getMovieDetails(ID)

      flow.collectLatest { actual ->
        verify(detailService).getMovieDetails(ID)
        Assert.assertEquals(ResponseWrapper.Success(expected), actual)

        verifyNoMoreInteractions(detailService)
      }
    }
  }

  @Test
  fun `Given when getTvDetails then return flow of response wrapper`() {
    val expected = Detail(
        id = ID,
        posterPath = POSTER_PATH,
        name = NAME,
        firstAirDate = FIRST_AIR_DATE,
        overview = null,
        voteAverage = VOTE_AVERAGE,
        genres = GENRES,
        originalLanguage = LANGUAGE
    )

    dispatcher.runBlockingTest {
      detailService.stub {
        onBlocking { getTvDetails(ID) } doReturn expected
      }

      val flow = detailRemoteDataSource.getTvDetails(ID)

      flow.collectLatest { actual ->
        verify(detailService).getTvDetails(ID)
        Assert.assertEquals(ResponseWrapper.Success(expected), actual)

        verifyNoMoreInteractions(detailService)
      }
    }
  }

  @Test
  fun `Given when getMovieCredits then return flow of response wrapper`() {
    val expected = getMediaCredits(Cast(
        id = CAST_ID,
        character = CAST_CHARACTER,
        name = CAST_NAME
    ))

    dispatcher.runBlockingTest {
      detailService.stub {
        onBlocking { getMovieCredits(ID) } doReturn expected
      }

      val flow = detailRemoteDataSource.getMovieCredits(ID)

      flow.collectLatest { actual ->
        verify(detailService).getMovieCredits(ID)
        Assert.assertEquals(ResponseWrapper.Success(expected), actual)

        verifyNoMoreInteractions(detailService)
      }
    }
  }

  @Test
  fun `Given when getTvCredits then return flow of response wrapper`() {
    val expected = getMediaCredits(Cast(
        id = CAST_ID,
        character = CAST_CHARACTER,
        name = CAST_NAME
    ))

    dispatcher.runBlockingTest {
      detailService.stub {
        onBlocking { getTvCredits(ID) } doReturn expected
      }

      val flow = detailRemoteDataSource.getTvCredits(ID)

      flow.collectLatest { actual ->
        verify(detailService).getTvCredits(ID)
        Assert.assertEquals(ResponseWrapper.Success(expected), actual)

        verifyNoMoreInteractions(detailService)
      }
    }
  }

  private fun getMediaCredits(cast: Cast) = MediaCredits(id = ID, cast = listOf(cast))
}