package com.syntia.moviecatalogue.core.di

import com.syntia.moviecatalogue.base.BuildConfig
import java.util.concurrent.TimeUnit
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_API_URL = "https://api.themoviedb.org/3/"

private const val CERTIFICATE_HOST_NAME = "api.themoviedb.org"

private const val API_KEY_QUERY = "api_key"
private const val AUTHORIZATION = "Authorization"
private const val BEARER_TOKEN_PREFIX = "Bearer "

val networkModule = module {

  fun provideRetrofit(): Retrofit {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
      setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    val interceptor = Interceptor { chain ->
      val request = chain.request()

      val newUrl = request.url.newBuilder()
          .addQueryParameter(API_KEY_QUERY, BuildConfig.THE_MOVIE_DB_API_KEY)
          .build()
      val headers = request.headers.newBuilder()
          .add(AUTHORIZATION, "$BEARER_TOKEN_PREFIX${BuildConfig.THE_MOVIE_DB_ACCESS_TOKEN}")
          .build()
      val requestBuilder = request.newBuilder()
          .url(newUrl)
          .headers(headers)

      chain.proceed(requestBuilder.build())
    }

    val movieApiCertificatePinner = CertificatePinner.Builder()
        .add(CERTIFICATE_HOST_NAME, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
        .add(CERTIFICATE_HOST_NAME, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
        .add(CERTIFICATE_HOST_NAME, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
        .build()

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
        .connectTimeout(2000, TimeUnit.SECONDS)
        .readTimeout(2000, TimeUnit.SECONDS)
        .certificatePinner(movieApiCertificatePinner)
        .build()

    return Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
  }

  single { provideRetrofit() }
}