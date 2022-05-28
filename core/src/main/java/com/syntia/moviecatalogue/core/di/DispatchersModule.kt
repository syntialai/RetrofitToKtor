package com.syntia.moviecatalogue.core.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dispatchersModule = module {
  fun provideIoDispatchers(): CoroutineDispatcher = Dispatchers.IO

  factory { provideIoDispatchers() }
}