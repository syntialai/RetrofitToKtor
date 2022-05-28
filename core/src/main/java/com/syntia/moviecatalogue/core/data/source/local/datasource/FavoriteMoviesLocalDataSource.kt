package com.syntia.moviecatalogue.core.data.source.local.datasource

import com.syntia.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteMoviesLocalDataSource {

  suspend fun getAllFavoriteMovies(page: Int, pageSize: Int): List<MovieEntity>

  fun getIsMovieExists(id: Int): Flow<Boolean>

  suspend fun addMovie(movie: MovieEntity)

  suspend fun updateMovieById(movie: MovieEntity)

  suspend fun deleteMovieById(id: Int)
}