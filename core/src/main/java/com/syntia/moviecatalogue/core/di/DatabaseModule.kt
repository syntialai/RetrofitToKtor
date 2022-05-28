package com.syntia.moviecatalogue.core.di

import android.app.Application
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.dsl.module

@InternalCoroutinesApi
val databaseModule = module {

  fun provideAppDatabase(applicationContext: Application): com.syntia.moviecatalogue.core.config.db.AppDatabase {
    return com.syntia.moviecatalogue.core.config.db.AppDatabase.getDatabase(applicationContext)
  }

  single { provideAppDatabase(get()) }
}