package com.syntia.moviecatalogue.core.utils.mapper

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.base.utils.DataMapper
import com.syntia.moviecatalogue.core.data.source.remote.response.search.KnownFor
import com.syntia.moviecatalogue.core.data.source.remote.response.search.SearchResult
import com.syntia.moviecatalogue.core.domain.model.search.SearchResultUiModel

object SearchMapper {

  fun toSearchResultUiModels(responses: ListItemResponse<SearchResult>) = responses.results.map {
    toSearchResultUiModel(it)
  }

  private fun toSearchResultUiModel(response: SearchResult) = SearchResultUiModel(
      id = response.id,
      image = response.posterPath ?: response.profilePath.orEmpty(),
      name = response.title ?: response.name.orEmpty(),
      releasedYear = DataMapper.getYear(response.releaseDate, response.firstAirDate),
      type = response.mediaType,
      voteAverage = response.voteAverage.toString(),
      adult = response.adult ?: false,
      knownFor = response.knownFor?.let {
        toKnownForString(it)
      }
  )

  private fun toKnownForString(knownForList: List<KnownFor>) = knownForList.filter {
    listOfNotNull(it.name, it.title).isNotEmpty()
  }.joinToString { knownFor ->
    knownFor.title ?: knownFor.name.orEmpty()
  }
}