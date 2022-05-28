package com.syntia.moviecatalogue.core.data.source.local.entity.tvShows

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.syntia.moviecatalogue.core.config.db.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_TV_SHOWS)
data class TvShowsEntity(

    @PrimaryKey
    val id: Int,

    val image: String,

    val title: String,

    val releasedYear: String,

    val voteAverage: String,

    val insertedAt: Long? = null
)