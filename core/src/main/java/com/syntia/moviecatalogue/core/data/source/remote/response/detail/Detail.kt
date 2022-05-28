package com.syntia.moviecatalogue.core.data.source.remote.response.detail

import com.google.gson.annotations.SerializedName

data class Detail(

    @SerializedName("id")
    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("runtime")
    val runtime: Int? = null,

    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = null,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("adult")
    val adult: Boolean? = false,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("overview")
    val overview: String?
)
