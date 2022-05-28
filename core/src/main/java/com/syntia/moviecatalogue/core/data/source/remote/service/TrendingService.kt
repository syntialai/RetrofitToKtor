package com.syntia.moviecatalogue.core.data.source.remote.service

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.config.api.ApiPath
import com.syntia.moviecatalogue.core.data.source.remote.response.movie.Movie
import com.syntia.moviecatalogue.core.data.source.remote.response.trending.TrendingItem
import com.syntia.moviecatalogue.core.data.source.remote.response.tvshow.TvShows
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingService {

  @GET(ApiPath.TRENDING_ALL_WEEK)
  suspend fun getTrendingItems(): ListItemResponse<TrendingItem>

  @GET(ApiPath.GET_MOVIE_POPULAR)
  suspend fun getPopularMovies(@Query(ApiPath.PAGE) page: Int): ListItemResponse<Movie>

  @GET(ApiPath.GET_TV_TOP_POPULAR)
  suspend fun getPopularTvShows(@Query(ApiPath.PAGE) page: Int): ListItemResponse<TvShows>
}