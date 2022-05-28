package com.syntia.moviecatalogue.core.data.source.local.datasource

import com.syntia.moviecatalogue.core.data.source.local.entity.tvShows.TvShowsEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteTvShowsLocalDataSource {

  suspend fun getAllFavoriteTvShows(page: Int, pageSize: Int): List<TvShowsEntity>

  fun getIsTvShowExists(id: Int): Flow<Boolean>

  suspend fun addTvShow(tvShows: TvShowsEntity)

  suspend fun updateTvShow(tvShows: TvShowsEntity)

  suspend fun deleteTvShowById(id: Int)
}