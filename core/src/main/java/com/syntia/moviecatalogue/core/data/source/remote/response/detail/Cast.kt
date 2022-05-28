package com.syntia.moviecatalogue.core.data.source.remote.response.detail

import com.google.gson.annotations.SerializedName

data class Cast(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("profile_path")
    val profilePath: String? = null,

    @SerializedName("character")
    val character: String,
)