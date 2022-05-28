package com.syntia.moviecatalogue.core.di

import com.syntia.moviecatalogue.core.data.source.local.dao.FavoriteMoviesDAO
import com.syntia.moviecatalogue.core.data.source.local.dao.FavoriteTvShowsDAO
import com.syntia.moviecatalogue.core.data.source.local.datasource.FavoriteMoviesLocalDataSource
import com.syntia.moviecatalogue.core.data.source.local.datasource.FavoriteTvShowsLocalDataSource
import com.syntia.moviecatalogue.core.data.source.local.datasource.impl.FavoriteMoviesLocalDataSourceImpl
import com.syntia.moviecatalogue.core.data.source.local.datasource.impl.FavoriteTvShowsLocalDataSourceImpl
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.dsl.bind
import org.koin.dsl.module

val localDataSourceModule = module {
  single { FavoriteMoviesLocalDataSourceImpl(get()) } bind FavoriteMoviesLocalDataSource::class
  single { FavoriteTvShowsLocalDataSourceImpl(get()) } bind FavoriteTvShowsLocalDataSource::class
}

@InternalCoroutinesApi
val daoModule = module {

  fun provideFavoriteMoviesDAO(appDatabase: com.syntia.moviecatalogue.core.config.db.AppDatabase): FavoriteMoviesDAO {
    return appDatabase.favoriteMoviesDAO()
  }

  fun provideFavoriteTvShowsDAO(appDatabase: com.syntia.moviecatalogue.core.config.db.AppDatabase): FavoriteTvShowsDAO {
    return appDatabase.favoriteTvShowsDAO()
  }

  single { provideFavoriteMoviesDAO(get()) }
  single { provideFavoriteTvShowsDAO(get()) }
}