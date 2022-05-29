package com.syntia.moviecatalogue.core.data.source.remote.response.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("character")
    val character: String
)