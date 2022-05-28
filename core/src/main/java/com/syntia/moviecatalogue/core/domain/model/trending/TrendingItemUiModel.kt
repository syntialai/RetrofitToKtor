package com.syntia.moviecatalogue.core.domain.model.trending

data class TrendingItemUiModel(

    val id: Int,

    val image: String,

    val title: String,

    val releasedYear: String,

    val type: String,

    val voteAverage: String)