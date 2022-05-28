package com.syntia.moviecatalogue.core.data.source.remote.response.detail

import com.google.gson.annotations.SerializedName

data class MediaCredits(

    @SerializedName("id")
    val id: Int,

    @SerializedName("cast")
    val cast: List<Cast>)
