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
import com.syntia.moviecatalogue.core.data.source.remote.service.impl.DetailServiceImpl
import com.syntia.moviecatalogue.core.data.source.remote.service.impl.SearchServiceImpl
import com.syntia.moviecatalogue.core.data.source.remote.service.impl.TrendingServiceImpl
import io.ktor.client.HttpClient
import org.koin.dsl.bind
import org.koin.dsl.module

val remoteDataSourceModule = module {
  single { DetailRemoteDataSourceImpl(get(), get()) } bind DetailRemoteDataSource::class
  single { SearchRemoteDataSourceImpl(get()) } bind SearchRemoteDataSource::class
  single { TrendingRemoteDataSourceImpl(get(), get()) } bind TrendingRemoteDataSource::class
}

val serviceModule = module {

  fun provideDetailService(httpClient: HttpClient): DetailService {
    return DetailServiceImpl(httpClient)
  }

  fun provideSearchService(httpClient: HttpClient): SearchService {
    return SearchServiceImpl(httpClient)
  }

  fun provideTrendingService(httpClient: HttpClient): TrendingService {
    return TrendingServiceImpl(httpClient)
  }

  single { provideDetailService(get()) }
  single { provideSearchService(get()) }
  single { provideTrendingService(get()) }
}