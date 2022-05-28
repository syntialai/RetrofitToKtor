package com.syntia.moviecatalogue.core.data.source.local.datasource.impl

import com.syntia.moviecatalogue.core.data.source.local.dao.FavoriteTvShowsDAO
import com.syntia.moviecatalogue.core.data.source.local.datasource.FavoriteTvShowsLocalDataSource
import com.syntia.moviecatalogue.core.data.source.local.entity.tvShows.TvShowsEntity
import kotlinx.coroutines.flow.Flow

class FavoriteTvShowsLocalDataSourceImpl(private val favoriteTvShowsDAO: FavoriteTvShowsDAO) :
    FavoriteTvShowsLocalDataSource {

  override suspend fun getAllFavoriteTvShows(page: Int, pageSize: Int): List<TvShowsEntity> {
    return favoriteTvShowsDAO.getAllFavoriteTvShows(page, pageSize)
  }

  override fun getIsTvShowExists(id: Int): Flow<Boolean> {
    return favoriteTvShowsDAO.getIsTvShowExists(id)
  }

  override suspend fun addTvShow(tvShows: TvShowsEntity) {
    favoriteTvShowsDAO.addTvShow(tvShows)
  }

  override suspend fun updateTvShow(tvShows: TvShowsEntity) {
    favoriteTvShowsDAO.updateTvShowById(tvShows.id, tvShows.title, tvShows.image,
        tvShows.releasedYear, tvShows.voteAverage)
  }

  override suspend fun deleteTvShowById(id: Int) {
    favoriteTvShowsDAO.deleteTvShowById(id)
  }
}