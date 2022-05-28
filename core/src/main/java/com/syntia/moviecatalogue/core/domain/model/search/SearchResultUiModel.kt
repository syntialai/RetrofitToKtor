package com.syntia.moviecatalogue.core.domain.model.search

data class SearchResultUiModel(

    val id: Int,

    val image: String,

    val name: String,

    val releasedYear: String,

    val type: String,

    val voteAverage: String,

    val adult: Boolean,

    val knownFor: String? = null)