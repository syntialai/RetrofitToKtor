package com.syntia.moviecatalogue.core.domain.repository

import com.syntia.moviecatalogue.base.domain.model.result.ResultWrapper
import com.syntia.moviecatalogue.core.domain.model.detail.CastUiModel
import com.syntia.moviecatalogue.core.domain.model.detail.DetailUiModel
import kotlinx.coroutines.flow.Flow

interface DetailRepository {

  suspend fun getDetails(mediaType: String, id: Int): Flow<ResultWrapper<DetailUiModel>>

  suspend fun getDetailCasts(mediaType: String, id: Int): Flow<ResultWrapper<MutableList<CastUiModel>>>
}