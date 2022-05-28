package com.syntia.moviecatalogue.core.data.source.remote.datasource

import com.syntia.moviecatalogue.base.data.remote.response.ResponseWrapper
import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.data.source.remote.response.movie.Movie
import com.syntia.moviecatalogue.core.data.source.remote.response.trending.TrendingItem
import com.syntia.moviecatalogue.core.data.source.remote.response.tvshow.TvShows
import kotlinx.coroutines.flow.Flow

interface TrendingRemoteDataSource {

  suspend fun getTrendingItems(): Flow<ResponseWrapper<ListItemResponse<TrendingItem>>>

  suspend fun getPopularMovies(page: Int): ListItemResponse<Movie>

  suspend fun getPopularTvShows(page: Int): ListItemResponse<TvShows>
}