package com.syntia.moviecatalogue.base.data.remote.response.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @SerializedName("status_message")
    val message: String
)
