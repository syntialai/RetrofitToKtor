package com.syntia.moviecatalogue.core.data.source.remote.service.impl

import com.syntia.moviecatalogue.base.data.remote.response.base.ListItemResponse
import com.syntia.moviecatalogue.core.config.api.ApiPath
import com.syntia.moviecatalogue.core.data.source.remote.response.movie.Movie
import com.syntia.moviecatalogue.core.data.source.remote.response.trending.TrendingItem
import com.syntia.moviecatalogue.core.data.source.remote.response.tvshow.TvShows
import com.syntia.moviecatalogue.core.data.source.remote.service.TrendingService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TrendingServiceImpl(private val httpClient: HttpClient) : TrendingService {

  override suspend fun getTrendingItems(): ListItemResponse<TrendingItem> {
    return httpClient.get(ApiPath.TRENDING_ALL_WEEK).body()
  }

  override suspend fun getPopularMovies(page: Int): ListItemResponse<Movie> {
    return httpClient.get(ApiPath.GET_MOVIE_POPULAR) {
      url {
        parameters.append(ApiPath.PAGE, page.toString())
      }
    }.body()
  }

  override suspend fun getPopularTvShows(page: Int): ListItemResponse<TvShows> {
    return httpClient.get(ApiPath.GET_TV_TOP_POPULAR) {
      url {
        parameters.append(ApiPath.PAGE, page.toString())
      }
    }.body()
  }
}