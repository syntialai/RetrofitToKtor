package com.syntia.moviecatalogue.core.data.source.remote.response.search

import com.google.gson.annotations.SerializedName

data class SearchResult(

    @SerializedName("adult")
    val adult: Boolean? = false,

    @SerializedName("first_air_date")
    val firstAirDate: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("known_for")
    val knownFor: List<KnownFor>?,

    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("profile_path")
    val profilePath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("vote_average")
    val voteAverage: Double
)