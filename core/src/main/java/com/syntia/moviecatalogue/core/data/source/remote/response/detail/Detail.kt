package com.syntia.moviecatalogue.core.data.source.remote.response.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Detail(

    @SerialName("id")
    val id: Int,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int? = null,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("adult")
    val adult: Boolean? = false,

    @SerialName("genres")
    val genres: List<Genre>,

    @SerialName("overview")
    val overview: String? = null
)
