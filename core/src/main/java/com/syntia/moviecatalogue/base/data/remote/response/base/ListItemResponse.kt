package com.syntia.moviecatalogue.base.data.remote.response.base

import com.google.gson.annotations.SerializedName

data class ListItemResponse<T>(

    @SerializedName("results")
    val results: List<T>)
