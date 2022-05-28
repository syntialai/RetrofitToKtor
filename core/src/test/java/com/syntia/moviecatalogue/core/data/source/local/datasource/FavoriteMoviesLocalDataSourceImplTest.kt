package com.syntia.moviecatalogue.core.data.source.local.datasource

import com.syntia.moviecatalogue.core.data.source.local.dao.FavoriteMoviesDAO
import com.syntia.moviecatalogue.core.data.source.local.datasource.impl.FavoriteMoviesLocalDataSourceImpl
import com.syntia.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
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
class FavoriteMoviesLocalDataSourceImplTest : BaseTest() {

  private lateinit var favoriteMoviesLocalDataSource: FavoriteMoviesLocalDataSource

  @Mock
  private lateinit var favoriteMoviesDAO: FavoriteMoviesDAO

  override fun setUp() {
    super.setUp()
    favoriteMoviesLocalDataSource = FavoriteMoviesLocalDataSourceImpl(favoriteMoviesDAO)
  }

  @Test
  fun `Given when getAllFavoriteMovies then return result from DAO`() {
    val expected = listOf(MovieEntity(
        id = ID,
        title = TITLE,
        image = IMAGE,
        releasedYear = YEAR,
        voteAverage = VOTE_AVERAGE_STRING,
        adult = false
    ))
    dispatcher.runBlockingTest {
      favoriteMoviesDAO.stub {
        onBlocking { getAllFavoriteMovies(PAGE, PAGE_SIZE) } doReturn expected
      }

      val actual = favoriteMoviesLocalDataSource.getAllFavoriteMovies(PAGE, PAGE_SIZE)

      verify(favoriteMoviesDAO).getAllFavoriteMovies(PAGE, PAGE_SIZE)
      assertEquals(expected, actual)

      verifyNoMoreInteractions(favoriteMoviesDAO)
    }
  }

  @Test
  fun `Given when getIsMovieExist then return result from DAO`() {
    val expected = true
    dispatcher.runBlockingTest {
      favoriteMoviesDAO.stub {
        onBlocking { getIsMovieExists(ID) } doReturn getFlow(expected)
      }

      val flow = favoriteMoviesLocalDataSource.getIsMovieExists(ID)

      flow.collectLatest { actual ->
        verify(favoriteMoviesDAO).getIsMovieExists(ID)
        assertEquals(expected, actual)

        verifyNoMoreInteractions(favoriteMoviesDAO)
      }
    }
  }

  @Test
  fun `Given when addMovie then verify DAO is called`() {
    val expected = MovieEntity(
        id = ID,
        title = TITLE,
        image = IMAGE,
        releasedYear = YEAR,
        voteAverage = VOTE_AVERAGE_STRING,
        adult = false
    )
    dispatcher.runBlockingTest {
      whenever(favoriteMoviesDAO.addMovie(expected)).thenReturn(Unit)

      favoriteMoviesLocalDataSource.addMovie(expected)

      verify(favoriteMoviesDAO).addMovie(expected)
      verifyNoMoreInteractions(favoriteMoviesDAO)
    }
  }

  @Test
  fun `Given when updateMovieById then verify DAO is called`() {
    val expected = MovieEntity(
        id = ID,
        title = TITLE,
        image = IMAGE,
        releasedYear = YEAR,
        voteAverage = VOTE_AVERAGE_STRING,
        adult = false
    )
    dispatcher.runBlockingTest {
      whenever(favoriteMoviesDAO.updateMovieById(expected.id, expected.title, expected.image,
          expected.releasedYear, expected.voteAverage, expected.adult)).thenReturn(Unit)

      favoriteMoviesLocalDataSource.updateMovieById(expected)

      verify(favoriteMoviesDAO).updateMovieById(expected.id, expected.title, expected.image,
          expected.releasedYear, expected.voteAverage, expected.adult)
      verifyNoMoreInteractions(favoriteMoviesDAO)
    }
  }

  @Test
  fun `Given when deleteMovieById then verify DAO is called`() {
    dispatcher.runBlockingTest {
      whenever(favoriteMoviesDAO.deleteMovieById(ID)).thenReturn(Unit)

      favoriteMoviesLocalDataSource.deleteMovieById(ID)

      verify(favoriteMoviesDAO).deleteMovieById(ID)
      verifyNoMoreInteractions(favoriteMoviesDAO)
    }
  }
}