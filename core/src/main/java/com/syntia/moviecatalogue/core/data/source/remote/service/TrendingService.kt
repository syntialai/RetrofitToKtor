package com.syntia.moviecatalogue.core.data.source.remote.service

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.data.source.remote.response.movie.Movie
import com.syntia.moviecatalogue.core.data.source.remote.response.trending.TrendingItem
import com.syntia.moviecatalogue.core.data.source.remote.response.tvshow.TvShows

interface TrendingService {

  suspend fun getTrendingItems(): ListItemResponse<TrendingItem>

  suspend fun getPopularMovies(page: Int): ListItemResponse<Movie>

  suspend fun getPopularTvShows(page: Int): ListItemResponse<TvShows>
}