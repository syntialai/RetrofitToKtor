package com.syntia.moviecatalogue.core.data.source.remote.datasource.impl

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.base.data.source.datasource.RemoteWrapResponse
import com.syntia.moviecatalogue.core.data.source.remote.datasource.TrendingRemoteDataSource
import com.syntia.moviecatalogue.core.data.source.remote.response.movie.Movie
import com.syntia.moviecatalogue.core.data.source.remote.response.trending.TrendingItem
import com.syntia.moviecatalogue.core.data.source.remote.response.tvshow.TvShows
import com.syntia.moviecatalogue.core.data.source.remote.service.TrendingService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class TrendingRemoteDataSourceImpl(private val trendingService: TrendingService,
    private val ioDispatcher: CoroutineDispatcher) : TrendingRemoteDataSource {

  override suspend fun getTrendingItems(): Flow<ResponseWrapper<ListItemResponse<TrendingItem>>> {
    return object : RemoteWrapResponse<ListItemResponse<TrendingItem>>() {
      override suspend fun fetchData(): ListItemResponse<TrendingItem> {
        return trendingService.getTrendingItems()
      }
    }.getResult(ioDispatcher)
  }

  override suspend fun getPopularMovies(page: Int): ListItemResponse<Movie> {
    return trendingService.getPopularMovies(page)
  }

  override suspend fun getPopularTvShows(page: Int): ListItemResponse<TvShows> {
    return trendingService.getPopularTvShows(page)
  }
}