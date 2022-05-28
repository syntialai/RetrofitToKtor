package com.syntia.moviecatalogue.core.data.source.remote.response.trending

import com.google.gson.annotations.SerializedName

data class TrendingItem(

    @SerializedName("adult")
    val adult: Boolean? = false,

    @SerializedName("first_air_date")
    val firstAirDate: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("vote_average")
    val voteAverage: Double
)