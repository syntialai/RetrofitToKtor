package com.syntia.moviecatalogue.core.utils.mapper

import com.syntia.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import com.syntia.moviecatalogue.core.data.source.local.entity.tvShows.TvShowsEntity
import com.syntia.moviecatalogue.core.domain.model.movie.MovieUiModel
import com.syntia.moviecatalogue.core.domain.model.tvshow.TvShowsUiModel

object FavoriteMapper {
  
  fun toMovieUiModels(movieEntities: List<MovieEntity>) = movieEntities.map { movieEntity -> 
    toMovieUiModel(movieEntity)
  }

  fun toTvShowUiModels(tvShowEntities: List<TvShowsEntity>) = tvShowEntities.map { tvShowsEntity ->
    toTvShowsUiModel(tvShowsEntity)
  }
  
  private fun toMovieUiModel(movieEntity: MovieEntity) = MovieUiModel(
      id = movieEntity.id,
      image = movieEntity.image,
      title = movieEntity.title,
      releasedYear = movieEntity.releasedYear,
      voteAverage = movieEntity.voteAverage,
      adult = movieEntity.adult
  )

  private fun toTvShowsUiModel(tvShowsEntity: TvShowsEntity) = TvShowsUiModel(
      id = tvShowsEntity.id,
      image = tvShowsEntity.image,
      title = tvShowsEntity.title,
      releasedYear = tvShowsEntity.releasedYear,
      voteAverage = tvShowsEntity.voteAverage
  )
}