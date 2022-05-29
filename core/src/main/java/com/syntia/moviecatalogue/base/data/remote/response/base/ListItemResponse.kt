package com.syntia.moviecatalogue.base.data.remote.response.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListItemResponse<T>(

    @SerialName("results")
    val results: List<T>? = null)
