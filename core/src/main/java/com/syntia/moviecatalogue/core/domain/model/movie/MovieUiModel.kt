package com.syntia.moviecatalogue.core.domain.model.movie

data class MovieUiModel(

    val id: Int,

    val image: String,

    val title: String,

    val releasedYear: String,

    val voteAverage: String,

    val adult: Boolean)