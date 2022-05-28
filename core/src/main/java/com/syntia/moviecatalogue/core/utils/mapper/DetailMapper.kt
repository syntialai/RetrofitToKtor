package com.syntia.moviecatalogue.core.utils.mapper

import com.syntia.moviecatalogue.base.utils.DataMapper
import com.syntia.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import com.syntia.moviecatalogue.core.data.source.local.entity.tvShows.TvShowsEntity
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Detail
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Genre
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.MediaCredits
import com.syntia.moviecatalogue.core.domain.model.detail.CastUiModel
import com.syntia.moviecatalogue.core.domain.model.detail.DetailUiModel
import com.syntia.moviecatalogue.core.domain.model.detail.GenreUiModel
import java.util.Locale
import kotlin.math.floor

object DetailMapper {

  fun toCastUiModels(response: MediaCredits): MutableList<CastUiModel> = response.cast.map { cast ->
    CastUiModel(
        id = cast.id,
        name = cast.name,
        image = cast.profilePath,
        character = cast.character
    )
  }.toMutableList()

  fun toDetailUiModel(response: Detail) = DetailUiModel(
      id = response.id,
      image = response.posterPath.orEmpty(),
      title = DataMapper.getTitle(response.title, response.name),
      releaseOrFirstAirDate = DataMapper.getFormattedDate(response.releaseDate, response.firstAirDate),
      language = response.originalLanguage.toUpperCase(Locale.ROOT),
      runtime = response.runtime?.let { getRuntime(it) },
      episodeCount = response.numberOfEpisodes ?: 0,
      isAdult = response.adult ?: false,
      genres = toGenreUiModels(response.genres),
      overview = response.overview,
      rating = getRating(response.voteAverage)
  )

  fun toMovieEntity(response: Detail) = MovieEntity(
      id = response.id,
      image = response.posterPath.orEmpty(),
      title = DataMapper.getTitle(response.title, response.name),
      releasedYear = DataMapper.getYear(response.releaseDate, response.firstAirDate),
      adult = response.adult ?: false,
      voteAverage = getRating(response.voteAverage).first
  )

  fun toMovieEntity(uiModel: DetailUiModel) = MovieEntity(
      id = uiModel.id,
      image = uiModel.image,
      title = uiModel.title,
      releasedYear = getReleasedYear(uiModel.releaseOrFirstAirDate),
      voteAverage = uiModel.rating.first,
      adult = uiModel.isAdult,
      insertedAt = System.currentTimeMillis()
  )

  fun toTvShowsEntity(response: Detail) = TvShowsEntity(
      id = response.id,
      image = response.posterPath.orEmpty(),
      title = DataMapper.getTitle(response.title, response.name),
      releasedYear = DataMapper.getYear(response.releaseDate, response.firstAirDate),
      voteAverage = getRating(response.voteAverage).first
  )

  fun toTvShowsEntity(uiModel: DetailUiModel) = TvShowsEntity(
      id = uiModel.id,
      image = uiModel.image,
      title = uiModel.title,
      releasedYear = getReleasedYear(uiModel.releaseOrFirstAirDate),
      voteAverage = uiModel.rating.first,
      insertedAt = System.currentTimeMillis()
  )

  private fun toGenreUiModels(genres: List<Genre>) = genres.map {
    GenreUiModel(it.id, it.name)
  }

  private fun getHour(minutes: Int) = floor((minutes / 60).toDouble()).toInt()

  private fun getRating(rating: Double) = Pair(rating.toString(), rating.toFloat().div(2f))

  private fun getRuntime(runtime: Int) = "${getHour(runtime)} h ${runtime % 60} min"

  private fun getReleasedYear(releaseOrFirstAirDate: String) = if (releaseOrFirstAirDate.isNotBlank()) {
    releaseOrFirstAirDate.substringAfterLast(", ")
  } else {
    ""
  }
}