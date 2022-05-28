package com.syntia.moviecatalogue.core.data.source.remote.response.search

import com.google.gson.annotations.SerializedName

data class KnownFor(

    @SerializedName("name")
    val name: String?,

    @SerializedName("title")
    val title: String?,
)
