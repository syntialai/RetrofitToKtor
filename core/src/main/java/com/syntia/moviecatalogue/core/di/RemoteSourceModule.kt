package com.syntia.moviecatalogue.core.di

import com.syntia.moviecatalogue.core.data.source.remote.datasource.DetailRemoteDataSource
import com.syntia.moviecatalogue.core.data.source.remote.datasource.SearchRemoteDataSource
import com.syntia.moviecatalogue.core.data.source.remote.datasource.TrendingRemoteDataSource
import com.syntia.moviecatalogue.core.data.source.remote.datasource.impl.DetailRemoteDataSourceImpl
import com.syntia.moviecatalogue.core.data.source.remote.datasource.impl.SearchRemoteDataSourceImpl
import com.syntia.moviecatalogue.core.data.source.remote.datasource.impl.TrendingRemoteDataSourceImpl
import com.syntia.moviecatalogue.core.data.source.remote.service.DetailService
import com.syntia.moviecatalogue.core.data.source.remote.service.SearchService
import com.syntia.moviecatalogue.core.data.source.remote.service.TrendingService
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteDataSourceModule = module {
  single { DetailRemoteDataSourceImpl(get(), get()) } bind DetailRemoteDataSource::class
  single { SearchRemoteDataSourceImpl(get()) } bind SearchRemoteDataSource::class
  single { TrendingRemoteDataSourceImpl(get(), get()) } bind TrendingRemoteDataSource::class
}

val serviceModule = module {

  fun provideDetailService(retrofit: Retrofit): DetailService {
    return retrofit.create(DetailService::class.java)
  }

  fun provideSearchService(retrofit: Retrofit): SearchService {
    return retrofit.create(SearchService::class.java)
  }

  fun provideTrendingService(retrofit: Retrofit): TrendingService {
    return retrofit.create(TrendingService::class.java)
  }

  single { provideDetailService(get()) }
  single { provideSearchService(get()) }
  single { provideTrendingService(get()) }
}