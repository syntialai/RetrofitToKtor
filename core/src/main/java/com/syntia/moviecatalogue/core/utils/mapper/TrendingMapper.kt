package com.syntia.moviecatalogue.core.utils.mapper

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.base.utils.DataMapper
import com.syntia.moviecatalogue.core.data.source.remote.response.movie.Movie
import com.syntia.moviecatalogue.core.data.source.remote.response.trending.TrendingItem
import com.syntia.moviecatalogue.core.data.source.remote.response.tvshow.TvShows
import com.syntia.moviecatalogue.core.domain.model.movie.MovieUiModel
import com.syntia.moviecatalogue.core.domain.model.trending.TrendingItemUiModel
import com.syntia.moviecatalogue.core.domain.model.tvshow.TvShowsUiModel
import java.util.Locale

object TrendingMapper {

  fun toTrendingItemUiModels(responses: ListItemResponse<TrendingItem>) = responses.results.map {
    toTrendingItemUiModel(it)
  }

  fun toMovieUiModels(responses: ListItemResponse<Movie>) = responses.results.map {
    toMovieUiModel(it)
  }

  fun toTvShowsUiModels(responses: ListItemResponse<TvShows>) = responses.results.map {
    toTvShowsUiModel(it)
  }

  private fun toTrendingItemUiModel(response: TrendingItem) = TrendingItemUiModel(
      id = response.id,
      title = DataMapper.getTitle(response.title, response.name),
      image = response.posterPath.orEmpty(),
      releasedYear = DataMapper.getYear(response.releaseDate, response.firstAirDate),
      voteAverage = response.voteAverage.toString(),
      type = response.mediaType.capitalize(Locale.ROOT))

  private fun toMovieUiModel(response: Movie) = MovieUiModel(
      id = response.id,
      image = response.posterPath.orEmpty(),
      title = response.title.orEmpty(),
      releasedYear = DataMapper.getYear(response.releaseDate),
      voteAverage = response.voteAverage.toString(),
      adult = response.adult)

  private fun toTvShowsUiModel(response: TvShows) = TvShowsUiModel(
      id = response.id,
      image = response.posterPath.orEmpty(),
      title = response.name,
      releasedYear = DataMapper.getYear(response.firstAirDate),
      voteAverage = response.voteAverage.toString())
}