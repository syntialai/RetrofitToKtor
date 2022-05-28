package com.syntia.moviecatalogue.favorite.helper

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Genre
import com.syntia.moviecatalogue.core.domain.model.detail.GenreUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
abstract class BaseTest {

  companion object {
    const val PAGE = 1
    const val PAGE_ZERO = 1
    const val PAGE_SIZE = 20
    const val QUERY = "QUERY"
    const val ID = 123
    const val DATE =  "2021-01-01"
    const val YEAR =  "2021"
    const val MEDIA_TYPE_MOVIE = "movie"
    const val MEDIA_TYPE_TV = "tv"
    const val NAME = "Finding Nemo"
    const val TITLE = "Finding Nemo"
    const val IMAGE = "/abc.jpg"
    const val VOTE_AVERAGE_STRING = "8.8"
    const val VOTE_AVERAGE_DIVIDED = 4.4f
    const val FIRST_AIR_DATE =  "2021-01-01"
    const val RELEASE_DATE = "2021-01-01"
    const val MEDIA_TYPE = "movie"
    const val POSTER_PATH = "/abc.jpg"
    const val VOTE_AVERAGE = 8.9
    const val LANGUAGE = "en"
    const val CAST_ID = 1234
    const val CAST_NAME = "Towel"
    const val CAST_CHARACTER = "OODFKS"
    const val CAST_IMAGE = "image"

    val GENRES = listOf(Genre(1, "Action"))
    val GENRES_UI_MODEL = listOf(GenreUiModel(1, "Action"))
  }

  protected val dispatcher = TestCoroutineDispatcher()

  @Before
  open fun setUp() {
    MockitoAnnotations.openMocks(this)
  }

  @After
  open fun tearDown() {
    Mockito.framework().clearInlineMocks()
    dispatcher.cleanupTestCoroutines()
  }

  protected fun <T> getFlow(data: T, isDelay: Boolean = false) = flow {
    if (isDelay) {
      delay(1000)
    }
    emit(data)
  }

  protected fun <T> generateListItemResponse(result: T) = ListItemResponse(results = listOf(result))
}