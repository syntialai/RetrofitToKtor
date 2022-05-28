package com.syntia.moviecatalogue

import android.app.Application
import com.syntia.moviecatalogue.core.di.daoModule
import com.syntia.moviecatalogue.core.di.databaseModule
import com.syntia.moviecatalogue.core.di.dispatchersModule
import com.syntia.moviecatalogue.core.di.localDataSourceModule
import com.syntia.moviecatalogue.core.di.networkModule
import com.syntia.moviecatalogue.core.di.remoteDataSourceModule
import com.syntia.moviecatalogue.core.di.repositoryModule
import com.syntia.moviecatalogue.core.di.serviceModule
import com.syntia.moviecatalogue.di.detailFeatureModule
import com.syntia.moviecatalogue.di.searchFeatureModule
import com.syntia.moviecatalogue.di.trendingFeatureModule
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@InternalCoroutinesApi
class MovieCatalogueApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger(Level.ERROR)
      androidContext(this@MovieCatalogueApplication)

      modules(listOf(dispatchersModule, networkModule, databaseModule, serviceModule, daoModule,
          remoteDataSourceModule, localDataSourceModule, repositoryModule, trendingFeatureModule,
          detailFeatureModule, searchFeatureModule))
    }
  }
}