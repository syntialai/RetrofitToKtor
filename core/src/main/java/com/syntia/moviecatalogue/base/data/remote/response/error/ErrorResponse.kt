package com.syntia.moviecatalogue.base.data.remote.response.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(

    @SerialName("status_message")
    val message: String
)
