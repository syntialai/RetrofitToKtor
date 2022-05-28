package com.syntia.moviecatalogue.core.data.repository

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.core.data.source.local.datasource.FavoriteMoviesLocalDataSource
import com.syntia.moviecatalogue.core.data.source.local.datasource.FavoriteTvShowsLocalDataSource
import com.syntia.moviecatalogue.core.data.source.remote.datasource.DetailRemoteDataSource
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Cast
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.MediaCredits
import com.syntia.moviecatalogue.core.domain.repository.DetailRepository
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
class DetailRepositoryImplTest : BaseTest() {

  private lateinit var detailRepository: DetailRepository

  @Mock
  private lateinit var detailRemoteDataSource: DetailRemoteDataSource

  @Mock
  private lateinit var favoriteMoviesLocalDataSource: FavoriteMoviesLocalDataSource

  @Mock
  private lateinit var favoriteTvShowsLocalDataSource: FavoriteTvShowsLocalDataSource

  override fun setUp() {
    super.setUp()
    detailRepository = DetailRepositoryImpl(detailRemoteDataSource, favoriteMoviesLocalDataSource,
        favoriteTvShowsLocalDataSource, dispatcher)
  }

  @Test
  fun `Given when getMovieCredits then return flow of response wrapper`() {
    val expected = getMediaCredits(Cast(
        id = CAST_ID,
        character = CAST_CHARACTER,
        name = CAST_NAME
    ))

    dispatcher.runBlockingTest {
      detailRemoteDataSource.stub {
        onBlocking { getMovieCredits(ID) } doReturn getFlow(ResponseWrapper.Success(expected))
      }

      val flow = detailRepository.getDetailCasts(MEDIA_TYPE_MOVIE, ID)

      flow.collectLatest {
        verify(detailRemoteDataSource).getMovieCredits(ID)
        verifyNoMoreInteractions(detailRemoteDataSource)
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
      detailRemoteDataSource.stub {
        onBlocking { getTvCredits(ID) } doReturn getFlow(ResponseWrapper.Success(expected))
      }

      val flow = detailRepository.getDetailCasts(MEDIA_TYPE_TV, ID)

      flow.collectLatest {
        verify(detailRemoteDataSource).getTvCredits(ID)
        verifyNoMoreInteractions(detailRemoteDataSource)
      }
    }
  }

  private fun getMediaCredits(cast: Cast) = MediaCredits(id = ID, cast = listOf(cast))
}