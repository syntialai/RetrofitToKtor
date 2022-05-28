package com.syntia.moviecatalogue.core.data.source.local.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.syntia.moviecatalogue.core.config.db.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_MOVIE)
data class MovieEntity(

    @PrimaryKey
    val id: Int,

    val image: String,

    val title: String,

    val releasedYear: String,

    val voteAverage: String,

    val adult: Boolean,

    val insertedAt: Long? = null
)