package com.syntia.moviecatalogue.core.data.repository

import androidx.paging.PagingData
import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.base.data.repository.RemoteMapResult
import com.syntia.moviecatalogue.base.data.repository.RemotePagingMapResult
import com.syntia.moviecatalogue.base.domain.model.result.ResultWrapper
import com.syntia.moviecatalogue.core.data.source.remote.datasource.TrendingRemoteDataSource
import com.syntia.moviecatalogue.core.data.source.remote.response.movie.Movie
import com.syntia.moviecatalogue.core.data.source.remote.response.trending.TrendingItem
import com.syntia.moviecatalogue.core.data.source.remote.response.tvshow.TvShows
import com.syntia.moviecatalogue.core.domain.model.movie.MovieUiModel
import com.syntia.moviecatalogue.core.domain.model.trending.TrendingItemUiModel
import com.syntia.moviecatalogue.core.domain.model.tvshow.TvShowsUiModel
import com.syntia.moviecatalogue.core.domain.repository.TrendingRepository
import com.syntia.moviecatalogue.core.utils.mapper.TrendingMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class TrendingRepositoryImpl(private val trendingRemoteDataSource: TrendingRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher) : TrendingRepository {

  override suspend fun getTrendingItems(): Flow<ResultWrapper<List<TrendingItemUiModel>>> {
    return object : RemoteMapResult<ListItemResponse<TrendingItem>, List<TrendingItemUiModel>>() {
      override suspend fun fetchData(): Flow<ResponseWrapper<ListItemResponse<TrendingItem>>> {
        return trendingRemoteDataSource.getTrendingItems()
      }

      override suspend fun mapData(
          data: ListItemResponse<TrendingItem>): List<TrendingItemUiModel> {
        return TrendingMapper.toTrendingItemUiModels(data)
      }
    }.getResult(ioDispatcher)
  }

  override suspend fun getPopularMovies(): Flow<PagingData<MovieUiModel>> {
    return object : RemotePagingMapResult<Movie, MovieUiModel>() {
      override suspend fun fetchData(page: Int): ListItemResponse<Movie> {
        return trendingRemoteDataSource.getPopularMovies(page)
      }

      override suspend fun mapData(data: ListItemResponse<Movie>): List<MovieUiModel> {
        return TrendingMapper.toMovieUiModels(data)
      }
    }.getResultFlow(ioDispatcher)
  }

  override suspend fun getPopularTvShows(): Flow<PagingData<TvShowsUiModel>> {
    return object : RemotePagingMapResult<TvShows, TvShowsUiModel>() {
      override suspend fun fetchData(page: Int): ListItemResponse<TvShows> {
        return trendingRemoteDataSource.getPopularTvShows(page)
      }

      override suspend fun mapData(data: ListItemResponse<TvShows>): List<TvShowsUiModel> {
        return TrendingMapper.toTvShowsUiModels(data)
      }
    }.getResultFlow(ioDispatcher)
  }
}