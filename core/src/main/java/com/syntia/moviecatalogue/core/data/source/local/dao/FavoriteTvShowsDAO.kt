package com.syntia.moviecatalogue.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syntia.moviecatalogue.core.data.source.local.entity.tvShows.TvShowsEntity
import kotlinx.coroutines.flow.Flow

@Dao interface FavoriteTvShowsDAO {

  @Query("SELECT * FROM tvShows ORDER BY insertedAt DESC LIMIT :pageSize OFFSET :page * :pageSize")
  suspend fun getAllFavoriteTvShows(page: Int, pageSize: Int): List<TvShowsEntity>

  @Query("SELECT EXISTS(SELECT * FROM tvShows WHERE id = :id)") fun getIsTvShowExists(
      id: Int): Flow<Boolean>

  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun addTvShow(tvShows: TvShowsEntity)

  @Query(
      "UPDATE tvShows SET title = :title, image = :image, releasedYear = :releasedYear, voteAverage = :voteAverage WHERE id = :id")
  suspend fun updateTvShowById(id: Int, title: String, image: String, releasedYear: String,
      voteAverage: String)

  @Query("DELETE FROM tvShows WHERE id = :id") suspend fun deleteTvShowById(id: Int)
}