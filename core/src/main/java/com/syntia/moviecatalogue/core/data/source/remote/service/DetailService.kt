package com.syntia.moviecatalogue.core.data.source.remote.service

import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Detail
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.MediaCredits
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {

  @GET(com.syntia.moviecatalogue.core.config.api.ApiPath.GET_MOVIE_ID)
  suspend fun getMovieDetails(@Path(com.syntia.moviecatalogue.core.config.api.ApiPath.ID) id: Int): Detail

  @GET(com.syntia.moviecatalogue.core.config.api.ApiPath.GET_TV_ID)
  suspend fun getTvDetails(@Path(com.syntia.moviecatalogue.core.config.api.ApiPath.ID) id: Int): Detail

  @GET(com.syntia.moviecatalogue.core.config.api.ApiPath.GET_MOVIE_ID_CREDITS)
  suspend fun getMovieCredits(@Path(com.syntia.moviecatalogue.core.config.api.ApiPath.ID) id: Int): MediaCredits

  @GET(com.syntia.moviecatalogue.core.config.api.ApiPath.GET_TV_ID_CREDITS)
  suspend fun getTvCredits(@Path(com.syntia.moviecatalogue.core.config.api.ApiPath.ID) id: Int): MediaCredits
}