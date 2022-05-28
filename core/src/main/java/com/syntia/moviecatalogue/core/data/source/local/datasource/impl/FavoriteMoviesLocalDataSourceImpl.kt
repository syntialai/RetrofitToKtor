package com.syntia.moviecatalogue.core.data.source.local.datasource.impl

import com.syntia.moviecatalogue.core.data.source.local.dao.FavoriteMoviesDAO
import com.syntia.moviecatalogue.core.data.source.local.datasource.FavoriteMoviesLocalDataSource
import com.syntia.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

class FavoriteMoviesLocalDataSourceImpl(private val favoriteMoviesDAO: FavoriteMoviesDAO) :
    FavoriteMoviesLocalDataSource {

  override suspend fun getAllFavoriteMovies(page: Int, pageSize: Int): List<MovieEntity> {
    return favoriteMoviesDAO.getAllFavoriteMovies(page, pageSize)
  }

  override fun getIsMovieExists(id: Int): Flow<Boolean> {
    return favoriteMoviesDAO.getIsMovieExists(id)
  }

  override suspend fun addMovie(movie: MovieEntity) {
    favoriteMoviesDAO.addMovie(movie)
  }

  override suspend fun updateMovieById(movie: MovieEntity) {
    favoriteMoviesDAO.updateMovieById(movie.id, movie.title, movie.image, movie.releasedYear,
        movie.voteAverage, movie.adult)
  }

  override suspend fun deleteMovieById(id: Int) {
    favoriteMoviesDAO.deleteMovieById(id)
  }
}