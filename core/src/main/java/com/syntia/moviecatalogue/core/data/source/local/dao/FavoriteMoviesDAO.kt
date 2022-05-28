package com.syntia.moviecatalogue.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syntia.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMoviesDAO {

  @Query("SELECT * FROM movies ORDER BY insertedAt DESC LIMIT :pageSize OFFSET :page * :pageSize")
  suspend fun getAllFavoriteMovies(page: Int, pageSize: Int): List<MovieEntity>

  @Query("SELECT EXISTS(SELECT * FROM movies WHERE id = :id)")
  fun getIsMovieExists(id: Int): Flow<Boolean>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addMovie(movie: MovieEntity)

  @Query(
      "UPDATE movies SET title = :title, image = :image, releasedYear = :releasedYear, voteAverage = :voteAverage, adult = :adult WHERE id = :id")
  suspend fun updateMovieById(id: Int, title: String, image: String, releasedYear: String,
      voteAverage: String, adult: Boolean)

  @Query("DELETE FROM movies WHERE id = :id")
  suspend fun deleteMovieById(id: Int)
}