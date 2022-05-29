package com.syntia.moviecatalogue.core.di

import android.util.Log
import com.syntia.moviecatalogue.base.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.CertificatePinner
import org.koin.dsl.module

private const val DOMAIN = "api.themoviedb.org/3"

private const val CERTIFICATE_HOST_NAME = "api.themoviedb.org"

private const val API_KEY_QUERY = "api_key"
private const val BEARER_TOKEN_PREFIX = "Bearer "

private const val TIME_OUT_MS = 120_000L

val networkModule = module {

  fun provideMovieApiCertificatePinner(): CertificatePinner {
    return CertificatePinner.Builder()
        .add(CERTIFICATE_HOST_NAME, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
        .add(CERTIFICATE_HOST_NAME, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
        .add(CERTIFICATE_HOST_NAME, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
        .build()
  }

  // Http Client with OkHttp configs
//  fun provideHttpClientKtor(
//      httpLoggingInterceptor: HttpLoggingInterceptor,
//      requestInterceptor: Interceptor,
//      movieApiCertificatePinner: CertificatePinner): HttpClient {
//    return HttpClient(OkHttp) {
//      engine {
//        config {
//          connectTimeout(TIME_OUT_MS, TimeUnit.SECONDS)
//          readTimeout(TIME_OUT_MS, TimeUnit.SECONDS)
//          certificatePinner(movieApiCertificatePinner)
//        }
//
//        addInterceptor(httpLoggingInterceptor)
//        addNetworkInterceptor(requestInterceptor)
//      }
//    }
//  }

  fun provideHttpClientAndroid(): HttpClient {
    return HttpClient(OkHttp) {
      expectSuccess = false

      install(Logging) {
        logger = object : Logger {
          override fun log(message: String) {
            Log.v("HttpClientLogger", message)
          }

        }
        level = LogLevel.ALL
      }

      install(HttpTimeout) {
        requestTimeoutMillis = TIME_OUT_MS
        connectTimeoutMillis = TIME_OUT_MS
        socketTimeoutMillis = TIME_OUT_MS
      }

      install(ContentNegotiation) {
        json(Json {
          prettyPrint = true
          isLenient = true
          ignoreUnknownKeys = true
        })
      }

      install(ResponseObserver) {
        onResponse { response ->
          Log.d("HttpClientLogger - HTTP status", "${response.status.value}")
          Log.d("HttpClientLogger - Response:", response.toString())
        }
      }

      install(DefaultRequest) {
        headers {
          append(HttpHeaders.ContentType, "application/json")
          append(HttpHeaders.Authorization, "$BEARER_TOKEN_PREFIX${BuildConfig.THE_MOVIE_DB_ACCESS_TOKEN}")
        }
        url {
          protocol = URLProtocol.HTTPS
          host = DOMAIN
          parameters.append(API_KEY_QUERY, BuildConfig.THE_MOVIE_DB_API_KEY)
        }
      }
    }
  }

  single { provideMovieApiCertificatePinner() }
  single { provideHttpClientAndroid() }
}