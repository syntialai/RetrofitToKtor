package com.syntia.moviecatalogue.core.data.repository

import com.syntia.moviecatalogue.core.data.source.local.datasource.FavoriteMoviesLocalDataSource
import com.syntia.moviecatalogue.core.data.source.local.datasource.FavoriteTvShowsLocalDataSource
import com.syntia.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import com.syntia.moviecatalogue.core.data.source.local.entity.tvShows.TvShowsEntity
import com.syntia.moviecatalogue.core.domain.model.detail.DetailUiModel
import com.syntia.moviecatalogue.core.domain.repository.FavoriteItemRepository
import com.syntia.moviecatalogue.core.helper.BaseTest
import com.syntia.moviecatalogue.core.utils.mapper.DetailMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FavoriteItemRepositoryImplTest : BaseTest() {

  private lateinit var favoriteItemRepository: FavoriteItemRepository

  @Mock
  private lateinit var favoriteMoviesLocalDataSource: FavoriteMoviesLocalDataSource

  @Mock
  private lateinit var favoriteTvShowsLocalDataSource: FavoriteTvShowsLocalDataSource

  private val movieEntityCaptor = argumentCaptor<MovieEntity>()

  private val tvShowEntityCaptor = argumentCaptor<TvShowsEntity>()

  private val pageCaptor = argumentCaptor<Int>()

  private val pageSizeCaptor = argumentCaptor<Int>()

  override fun setUp() {
    super.setUp()
    favoriteItemRepository = FavoriteItemRepositoryImpl(favoriteMoviesLocalDataSource,
        favoriteTvShowsLocalDataSource, dispatcher)
  }

  @Test
  fun `Given when getIsMovieExist then return flow of boolean from data source`() {
    val expected = true

    dispatcher.runBlockingTest {
      whenever(favoriteMoviesLocalDataSource.getIsMovieExists(ID)) doReturn getFlow(true)

      val flow = favoriteItemRepository.getIsMovieExist(ID)

      flow.collectLatest { actual ->
        verify(favoriteMoviesLocalDataSource).getIsMovieExists(ID)
        assertEquals(expected, actual)

        verifyNoMoreInteractions(favoriteMoviesLocalDataSource)
      }
    }
  }

  @Test
  fun `Given when getIsTvShowExist then return flow of boolean from data source`() {
    val expected = true

    dispatcher.runBlockingTest {
      whenever(favoriteTvShowsLocalDataSource.getIsTvShowExists(ID)) doReturn getFlow(true)

      val flow = favoriteItemRepository.getIsTvShowExist(ID)

      flow.collectLatest { actual ->
        verify(favoriteTvShowsLocalDataSource).getIsTvShowExists(ID)
        assertEquals(expected, actual)

        verifyNoMoreInteractions(favoriteTvShowsLocalDataSource)
      }
    }
  }

  @Test
  fun `Given when addMovie then verify data source is called`() {
    val uiModel = DetailUiModel(
        id = ID,
        image = POSTER_PATH,
        title = TITLE,
        releaseOrFirstAirDate = RELEASE_DATE,
        overview = null,
        rating = Pair(VOTE_AVERAGE_STRING, VOTE_AVERAGE_DIVIDED),
        genres = GENRES_UI_MODEL,
        isAdult = false,
        language = "en",
        episodeCount = 0,
        runtime = "2hr"
    )
    val expected = DetailMapper.toMovieEntity(uiModel)

    dispatcher.runBlockingTest {
      whenever(favoriteMoviesLocalDataSource.addMovie(any())).thenReturn(Unit)

      favoriteItemRepository.addMovie(uiModel)

      verify(favoriteMoviesLocalDataSource).addMovie(movieEntityCaptor.capture())
      with(movieEntityCaptor.firstValue) {
        assertEquals(expected.id, id)
        assertEquals(expected.image, image)
        assertEquals(expected.title, title)
        assertEquals(expected.releasedYear, releasedYear)
        assertEquals(expected.voteAverage, voteAverage)
        assertEquals(expected.adult, adult)
      }

      verifyNoMoreInteractions(favoriteMoviesLocalDataSource)
    }
  }

  @Test
  fun `Given when addTvShows then verify data source is called`() {
    val uiModel = DetailUiModel(
        id = ID,
        image = POSTER_PATH,
        title = TITLE,
        releaseOrFirstAirDate = RELEASE_DATE,
        overview = null,
        rating = Pair(VOTE_AVERAGE_STRING, VOTE_AVERAGE_DIVIDED),
        genres = GENRES_UI_MODEL,
        isAdult = false,
        language = "en",
        episodeCount = 0,
        runtime = "2hr"
    )
    val expected = DetailMapper.toTvShowsEntity(uiModel)

    dispatcher.runBlockingTest {
      whenever(favoriteTvShowsLocalDataSource.addTvShow(any())).thenReturn(Unit)

      favoriteItemRepository.addTvShows(uiModel)

      verify(favoriteTvShowsLocalDataSource).addTvShow(tvShowEntityCaptor.capture())
      with(tvShowEntityCaptor.firstValue) {
        assertEquals(expected.id, id)
        assertEquals(expected.image, image)
        assertEquals(expected.title, title)
        assertEquals(expected.releasedYear, releasedYear)
        assertEquals(expected.voteAverage, voteAverage)
      }

      verifyNoMoreInteractions(favoriteTvShowsLocalDataSource)
    }
  }

  @Test
  fun `Given when deleteMovieById then verify data source is called`() {
    dispatcher.runBlockingTest {
      whenever(favoriteMoviesLocalDataSource.deleteMovieById(ID)).thenReturn(Unit)

      favoriteItemRepository.deleteMovieById(ID)

      verify(favoriteMoviesLocalDataSource).deleteMovieById(ID)
      verifyNoMoreInteractions(favoriteMoviesLocalDataSource)
    }
  }

  @Test
  fun `Given when deleteTvShowsById then verify data source is called`() {
    dispatcher.runBlockingTest {
      whenever(favoriteTvShowsLocalDataSource.deleteTvShowById(ID)).thenReturn(Unit)

      favoriteItemRepository.deleteTvShowsById(ID)

      verify(favoriteTvShowsLocalDataSource).deleteTvShowById(ID)
      verifyNoMoreInteractions(favoriteTvShowsLocalDataSource)
    }
  }
}