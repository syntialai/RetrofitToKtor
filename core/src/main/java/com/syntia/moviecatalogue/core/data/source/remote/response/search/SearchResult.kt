package com.syntia.moviecatalogue.core.data.source.remote.response.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(

    @SerialName("adult")
    var adult: Boolean? = false,

    @SerialName("first_air_date")
    var firstAirDate: String? = null,

    @SerialName("id")
    var id: Int,

    @SerialName("known_for")
    var knownFor: List<KnownFor>? = null,

    @SerialName("media_type")
    var mediaType: String,

    @SerialName("name")
    var name: String? = null,

    @SerialName("poster_path")
    var posterPath: String? = null,

    @SerialName("profile_path")
    var profilePath: String? = null,

    @SerialName("release_date")
    var releaseDate: String? = null,

    @SerialName("title")
    var title: String? = null,

    @SerialName("vote_average")
    var voteAverage: Double? = null
)