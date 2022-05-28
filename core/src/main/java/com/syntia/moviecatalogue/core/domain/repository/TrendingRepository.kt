package com.syntia.moviecatalogue.core.domain.repository

import androidx.paging.PagingData
import com.syntia.moviecatalogue.base.domain.model.result.ResultWrapper
import com.syntia.moviecatalogue.core.domain.model.movie.MovieUiModel
import com.syntia.moviecatalogue.core.domain.model.trending.TrendingItemUiModel
import com.syntia.moviecatalogue.core.domain.model.tvshow.TvShowsUiModel
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {

  suspend fun getTrendingItems(): Flow<ResultWrapper<List<TrendingItemUiModel>>>

  suspend fun getPopularMovies(): Flow<PagingData<MovieUiModel>>

  suspend fun getPopularTvShows(): Flow<PagingData<TvShowsUiModel>>
}