package com.syntia.moviecatalogue.core.data.source.remote.response.trending

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingItem(

    @SerialName("adult")
    val adult: Boolean? = false,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("id")
    val id: Int,

    @SerialName("media_type")
    val mediaType: String,

    @SerialName("name")
    val name: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double
)