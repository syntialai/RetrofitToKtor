package com.syntia.moviecatalogue.core.data.source.remote.response.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KnownFor(

    @SerialName("name")
    val name: String? = null,

    @SerialName("title")
    val title: String? = null,
)
