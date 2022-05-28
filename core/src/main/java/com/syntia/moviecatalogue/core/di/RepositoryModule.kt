package com.syntia.moviecatalogue.core.di

import com.syntia.moviecatalogue.core.data.repository.DetailRepositoryImpl
import com.syntia.moviecatalogue.core.data.repository.FavoriteItemRepositoryImpl
import com.syntia.moviecatalogue.core.data.repository.SearchRepositoryImpl
import com.syntia.moviecatalogue.core.data.repository.TrendingRepositoryImpl
import com.syntia.moviecatalogue.core.domain.repository.DetailRepository
import com.syntia.moviecatalogue.core.domain.repository.FavoriteItemRepository
import com.syntia.moviecatalogue.core.domain.repository.SearchRepository
import com.syntia.moviecatalogue.core.domain.repository.TrendingRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
  single { TrendingRepositoryImpl(get(), get()) } bind TrendingRepository::class
  single { DetailRepositoryImpl(get(), get(), get(), get()) } bind DetailRepository::class
  single {
    FavoriteItemRepositoryImpl(get(), get(), get())
  } bind FavoriteItemRepository::class
  single { SearchRepositoryImpl(get(), get()) } bind SearchRepository::class
}