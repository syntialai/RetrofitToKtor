package com.syntia.moviecatalogue.core.domain.repository

import androidx.paging.PagingData
import com.syntia.moviecatalogue.core.domain.model.detail.DetailUiModel
import com.syntia.moviecatalogue.core.domain.model.movie.MovieUiModel
import com.syntia.moviecatalogue.core.domain.model.tvshow.TvShowsUiModel
import kotlinx.coroutines.flow.Flow

interface FavoriteItemRepository {

  suspend fun getFavoriteMovies(): Flow<PagingData<MovieUiModel>>

  suspend fun getFavoriteTvShows(): Flow<PagingData<TvShowsUiModel>>

  suspend fun getIsMovieExist(id: Int): Flow<Boolean>

  suspend fun getIsTvShowExist(id: Int): Flow<Boolean>

  suspend fun addMovie(detailUiModel: DetailUiModel)

  suspend fun addTvShows(detailUiModel: DetailUiModel)

  suspend fun deleteMovieById(id: Int)

  suspend fun deleteTvShowsById(id: Int)
}