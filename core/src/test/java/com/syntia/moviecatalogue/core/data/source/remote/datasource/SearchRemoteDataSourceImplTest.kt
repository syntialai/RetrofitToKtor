package com.syntia.moviecatalogue.core.data.source.remote.datasource

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.data.source.remote.datasource.impl.SearchRemoteDataSourceImpl
import com.syntia.moviecatalogue.core.data.source.remote.response.search.SearchResult
import com.syntia.moviecatalogue.core.data.source.remote.service.SearchService
import com.syntia.moviecatalogue.core.helper.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
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
class SearchRemoteDataSourceImplTest : BaseTest() {

  private lateinit var searchRemoteDataSource: SearchRemoteDataSource

  @Mock
  private lateinit var searchService: SearchService

  override fun setUp() {
    super.setUp()
    searchRemoteDataSource = SearchRemoteDataSourceImpl(searchService)
  }

  @Test
  fun `Given when searchByQuery then return result from service`() {
    val expected = ListItemResponse(listOf(SearchResult(
        firstAirDate = FIRST_AIR_DATE,
        id = ID,
        posterPath = POSTER_PATH,
        mediaType = MEDIA_TYPE,
        voteAverage = VOTE_AVERAGE,
        name = NAME,
        releaseDate = null,
        title = null,
        adult = false,
        profilePath = null,
        knownFor = null
    )))

    dispatcher.runBlockingTest {
      searchService.stub {
        onBlocking { searchByQuery(PAGE, QUERY) } doReturn expected
      }

      val actual = searchRemoteDataSource.searchByQuery(PAGE, QUERY)

      verify(searchService).searchByQuery(PAGE, QUERY)
      assertEquals(expected, actual)

      verifyNoMoreInteractions(searchService)
    }
  }
}