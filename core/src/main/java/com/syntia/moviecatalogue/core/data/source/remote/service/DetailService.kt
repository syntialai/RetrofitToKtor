package com.syntia.moviecatalogue.core.data.source.remote.service

import com.syntia.moviecatalogue.core.data.source.remote.response.detail.Detail
import com.syntia.moviecatalogue.core.data.source.remote.response.detail.MediaCredits

interface DetailService {

  suspend fun getMovieDetails(id: Int): Detail

  suspend fun getTvDetails(id: Int): Detail

  suspend fun getMovieCredits(id: Int): MediaCredits

  suspend fun getTvCredits(id: Int): MediaCredits
}