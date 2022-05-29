package com.syntia.moviecatalogue.core.data.source.remote.service.impl

import com.syntia.moviecatalogue.core.config.api.ApiPath
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Detail
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.MediaCredits
import com.syntia.moviecatalogue.core.data.source.remote.service.DetailService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class DetailServiceImpl(private val httpClient: HttpClient) : DetailService {

  override suspend fun getMovieDetails(id: Int): Detail {
    return httpClient.get {
      url {
        appendPathSegments(ApiPath.MOVIE, id.toString())
      }
    }.body()
  }

  override suspend fun getTvDetails(id: Int): Detail {
    return httpClient.get {
      url {
        appendPathSegments(ApiPath.TV, id.toString())
      }
    }.body()
  }

  override suspend fun getMovieCredits(id: Int): MediaCredits {
    val path = String.format(ApiPath.GET_MOVIE_STRING_CREDITS, id)
    return httpClient.get(path).body()
  }

  override suspend fun getTvCredits(id: Int): MediaCredits {
    val path = String.format(ApiPath.GET_TV_STRING_CREDITS, id)
    return httpClient.get(path).body()
  }
}