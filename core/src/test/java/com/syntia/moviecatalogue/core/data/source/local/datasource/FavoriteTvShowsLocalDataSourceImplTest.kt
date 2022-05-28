package com.syntia.moviecatalogue.core.data.source.local.datasource

import com.syntia.moviecatalogue.core.data.source.local.dao.FavoriteTvShowsDAO
import com.syntia.moviecatalogue.core.data.source.local.datasource.impl.FavoriteTvShowsLocalDataSourceImpl
import com.syntia.moviecatalogue.core.data.source.local.entity.tvShows.TvShowsEntity
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
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FavoriteTvShowsLocalDataSourceImplTest : BaseTest() {

  private lateinit var favoriteTvShowsLocalDataSource: FavoriteTvShowsLocalDataSource

  @Mock
  private lateinit var favoriteTvShowsDAO: FavoriteTvShowsDAO

  override fun setUp() {
    super.setUp()
    favoriteTvShowsLocalDataSource = FavoriteTvShowsLocalDataSourceImpl(favoriteTvShowsDAO)
  }

  @Test
  fun `Given when getAllFavoriteTvShows then return result from DAO`() {
    val expected = listOf(TvShowsEntity(
        id = ID,
        title = TITLE,
        image = IMAGE,
        releasedYear = YEAR,
        voteAverage = VOTE_AVERAGE_STRING
    ))

    dispatcher.runBlockingTest {
      favoriteTvShowsDAO.stub {
        onBlocking { getAllFavoriteTvShows(PAGE, PAGE_SIZE) } doReturn expected
      }

      val actual = favoriteTvShowsLocalDataSource.getAllFavoriteTvShows(PAGE, PAGE_SIZE)

      verify(favoriteTvShowsDAO).getAllFavoriteTvShows(PAGE, PAGE_SIZE)
      assertEquals(expected, actual)

      verifyNoMoreInteractions(favoriteTvShowsDAO)
    }
  }

  @Test
  fun `Given when getIsTvShowExist then return result from DAO`() {
    val expected = true
    dispatcher.runBlockingTest {
      favoriteTvShowsDAO.stub {
        onBlocking { getIsTvShowExists(ID) } doReturn getFlow(expected)
      }

      val flow = favoriteTvShowsLocalDataSource.getIsTvShowExists(ID)

      flow.collectLatest { actual ->
        verify(favoriteTvShowsDAO).getIsTvShowExists(ID)
        assertEquals(expected, actual)

        verifyNoMoreInteractions(favoriteTvShowsDAO)
      }
    }
  }

  @Test
  fun `Given when addTvShow then verify DAO is called`() {
    val expected = TvShowsEntity(
        id = ID,
        title = TITLE,
        image = IMAGE,
        releasedYear = YEAR,
        voteAverage = VOTE_AVERAGE_STRING
    )
    dispatcher.runBlockingTest {
      whenever(favoriteTvShowsDAO.addTvShow(expected)).thenReturn(Unit)

      favoriteTvShowsLocalDataSource.addTvShow(expected)

      verify(favoriteTvShowsDAO).addTvShow(expected)
      verifyNoMoreInteractions(favoriteTvShowsDAO)
    }
  }

  @Test
  fun `Given when updateTvShowById then verify DAO is called`() {
    val expected = TvShowsEntity(
        id = ID,
        title = TITLE,
        image = IMAGE,
        releasedYear = YEAR,
        voteAverage = VOTE_AVERAGE_STRING
    )
    dispatcher.runBlockingTest {
      whenever(favoriteTvShowsDAO.updateTvShowById(expected.id, expected.title, expected.image,
          expected.releasedYear, expected.voteAverage)).thenReturn(Unit)

      favoriteTvShowsLocalDataSource.updateTvShow(expected)

      verify(favoriteTvShowsDAO).updateTvShowById(expected.id, expected.title, expected.image,
          expected.releasedYear, expected.voteAverage)
      verifyNoMoreInteractions(favoriteTvShowsDAO)
    }
  }

  @Test
  fun `Given when deleteTvShowById then verify DAO is called`() {
    dispatcher.runBlockingTest {
      whenever(favoriteTvShowsDAO.deleteTvShowById(ID)).thenReturn(Unit)

      favoriteTvShowsLocalDataSource.deleteTvShowById(ID)

      verify(favoriteTvShowsDAO).deleteTvShowById(ID)
      verifyNoMoreInteractions(favoriteTvShowsDAO)
    }
  }
}