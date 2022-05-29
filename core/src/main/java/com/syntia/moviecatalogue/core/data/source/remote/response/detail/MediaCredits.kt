package com.syntia.moviecatalogue.core.data.source.remote.response.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaCredits(

    @SerialName("id")
    val id: Int,

    @SerialName("cast")
    val cast: List<Cast>)
