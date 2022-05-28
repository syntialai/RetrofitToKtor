package com.syntia.moviecatalogue.core.data.repository

import androidx.paging.PagingData
import com.syntia.moviecatalogue.base.data.repository.LocalPagingMapResult
import com.syntia.moviecatalogue.core.data.source.local.datasource.FavoriteMoviesLocalDataSource
import com.syntia.moviecatalogue.core.data.source.local.datasource.FavoriteTvShowsLocalDataSource
import com.syntia.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import com.syntia.moviecatalogue.core.data.source.local.entity.tvShows.TvShowsEntity
import com.syntia.moviecatalogue.core.domain.model.detail.DetailUiModel
import com.syntia.moviecatalogue.core.domain.model.movie.MovieUiModel
import com.syntia.moviecatalogue.core.domain.model.tvshow.TvShowsUiModel
import com.syntia.moviecatalogue.core.domain.repository.FavoriteItemRepository
import com.syntia.moviecatalogue.core.utils.mapper.DetailMapper
import com.syntia.moviecatalogue.core.utils.mapper.FavoriteMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FavoriteItemRepositoryImpl(
    private val favoriteMoviesLocalDataSource: FavoriteMoviesLocalDataSource,
    private val favoriteTvShowsLocalDataSource: FavoriteTvShowsLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher) : FavoriteItemRepository {

  override suspend fun getFavoriteMovies(): Flow<PagingData<MovieUiModel>> {
    return object : LocalPagingMapResult<MovieEntity, MovieUiModel>() {
      override suspend fun fetchData(page: Int, pageSize: Int): List<MovieEntity> {
        return favoriteMoviesLocalDataSource.getAllFavoriteMovies(page, pageSize)
      }

      override suspend fun mapData(data: List<MovieEntity>): List<MovieUiModel> {
        return FavoriteMapper.toMovieUiModels(data)
      }
    }.getResultFlow(ioDispatcher)
  }

  override suspend fun getFavoriteTvShows(): Flow<PagingData<TvShowsUiModel>> {
    return object : LocalPagingMapResult<TvShowsEntity, TvShowsUiModel>() {
      override suspend fun fetchData(page: Int, pageSize: Int): List<TvShowsEntity> {
        return favoriteTvShowsLocalDataSource.getAllFavoriteTvShows(page, pageSize)
      }

      override suspend fun mapData(data: List<TvShowsEntity>): List<TvShowsUiModel> {
        return FavoriteMapper.toTvShowUiModels(data)
      }
    }.getResultFlow(ioDispatcher)
  }

  override suspend fun getIsMovieExist(id: Int): Flow<Boolean> {
    return favoriteMoviesLocalDataSource.getIsMovieExists(id).flowOn(ioDispatcher)
  }

  override suspend fun getIsTvShowExist(id: Int): Flow<Boolean> {
    return favoriteTvShowsLocalDataSource.getIsTvShowExists(id).flowOn(ioDispatcher)
  }

  override suspend fun addMovie(detailUiModel: DetailUiModel) {
    val movie = DetailMapper.toMovieEntity(detailUiModel)
    favoriteMoviesLocalDataSource.addMovie(movie)
  }

  override suspend fun addTvShows(detailUiModel: DetailUiModel) {
    val tvShows = DetailMapper.toTvShowsEntity(detailUiModel)
    favoriteTvShowsLocalDataSource.addTvShow(tvShows)
  }

  override suspend fun deleteMovieById(id: Int) {
    favoriteMoviesLocalDataSource.deleteMovieById(id)
  }

  override suspend fun deleteTvShowsById(id: Int) {
    favoriteTvShowsLocalDataSource.deleteTvShowById(id)
  }
}