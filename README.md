# RetrofitToKtor

A movie catalogue application sample to preview data from [The Movie DB API](https://www.themoviedb.org/). 

## Demo

https://user-images.githubusercontent.com/53588149/170821249-2d183032-2577-4a5f-b282-9d82ab9281e3.mov

## Ktor Migration Guideline

+ Setup some dependencies for supporting ktor
  + In the `build.gradle` (project level, located at the project root), add `kotlin-serialization` dependency

    ```gradle
    dependencies {
      classpath "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"
    }
    ```

  + Add the following changes in the `build.gradle (:core)` (core module level)

    ```gradle
    plugins {
      ...
      id 'kotlinx-serialization'
    }
    ....
    .........
    dependencies {
      ...
      
      // Ktor
      implementation 'io.ktor:ktor-client-android:2.0.2'
      implementation "io.ktor:ktor-client-okhttp:2.0.2"
      implementation "io.ktor:ktor-client-content-negotiation:2.0.2"
      implementation 'io.ktor:ktor-serialization-kotlinx-json:2.0.2'
      implementation 'io.ktor:ktor-client-logging-jvm:2.0.2'
    }
    ```

    + `client-android` provides Android client engine to processes network request on Android.
    + `client-okhttp` provides OkHttp client engine to processes network request on Android with OkHttp utility.
    + `content-negotiation` provides utility to serialize/deserialize content in a specific format (JSON, XML, CBOR).
    + `serialization-kotlinx-json` serialize/deserialize content to format JSON.
    + `client-logging-jvm` provides utility to put logger to HttpClient when processing the request.

+ Configure the HttpClient engine in `NetworkModule.kt`

  ```kotlin
  private const val DOMAIN = "api.themoviedb.org/3"

  private const val CERTIFICATE_HOST_NAME = "api.themoviedb.org"

  private const val API_KEY_QUERY = "api_key"
  private const val BEARER_TOKEN_PREFIX = "Bearer "

  private const val TIME_OUT_MS = 120_000L

  val networkModule = module {

    ...
    fun provideHttpClientAndroid(): HttpClient { // 1
      return HttpClient(OkHttp) { // 2
        expectSuccess = false

        install(Logging) { // 3
          logger = object : Logger {
            override fun log(message: String) {
              Log.v("HttpClientLogger", message)
            }

          }
          level = LogLevel.ALL
        }

        install(HttpTimeout) { // 4
          requestTimeoutMillis = TIME_OUT_MS
          connectTimeoutMillis = TIME_OUT_MS
          socketTimeoutMillis = TIME_OUT_MS
        }

        install(ContentNegotiation) { // 5
          json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
          })
        }

        install(ResponseObserver) { // 6
          onResponse { response ->
            Log.d("HttpClientLogger - HTTP status", "${response.status.value}")
            Log.d("HttpClientLogger - Response:", response.toString())
          }
        }

        install(DefaultRequest) { // 7
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

    ...
    single { provideHttpClientAndroid() } // 8
  }
  ```

  From the code above, we did these followings:
    1. Create a method that return a `HttpClient` for making API requests with Ktor
    2. Instantiate a `HttpClient` with `OkHttp` as the engine
    3. Configure logger on `HttpClient` to put logs on request processes
    4. Configure time out in milliseconds for every single request
    5. Configure content serialization from JSON response to data class, and vice versa
    6. Add logger utility on response received from http client
    7. Configure request builder (headers, url, params, etc) for all http client
    8. Provide a singleton http client for injection

+ Add implementation for all services in core module
  + Add `HttpClient` as constructor
  + On each implementation method, return `httpClient.get(path).body()` to make request with http method `GET` and modify as Retrofit interface needs
    + Add query parameters by modifying the http request builder url parameters, for example:
      ```kotlin
      httpClient.get(ApiPath.SEARCH_MULTI) {
        url {
          parameters.append(ApiPath.PAGE, page.toString())
          parameters.append(ApiPath.QUERY, query)
        }
      }.body()
      ```

    + Add path variable by passing formatted string to get method directly:
      ```kotlin
      val path = String.format("movie/%s/credits", id)
      return httpClient.get(path).body()
      ```
      , or modify the http request builder url pathSegment:
      ```kotlin
      httpClient.get {
        url {
          appendPathSegments(ApiPath.MOVIE, id.toString())
        }
      }.body()
      ```
      
    + Add request body by `setBody(requestBody)` method in http request builder
      ```kotlin
      httpClient.get(path) {
        setBody(Genre("id", "name"))
      }
      ```
      
  Refer to [Ktor request documentation](https://ktor.io/docs/request.html) for more information.

+ Add serialization support to all response data classes (`com.syntia.moviecatalogue.core.data.source.remote.response.*` and `com.syntia.moviecatalogue.base.data.remote.response.*`)
  + Add annotation `@Serializable` to support serialization
  + Change annotation `@SerializedName("name")` from Retrofit to `@SerialName("name")` from `kotlinx` to set the key name manually when serialization
  + Add default `null` value for all nullable field

+ Modify error handler and add exception from Ktor
  + Add catch exception to `ClientRequestException` and `ServerResponseException` to `BasePagingSource`

    ```kotlin
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, U> {
      ...
      return try {
      val responseData = getResult(pageIndex)
        LoadResult.Page(
            data = responseData,
            prevKey = if (pageIndex == initialPage) null else pageIndex - 1,
            nextKey = if (responseData.isEmpty()) null else pageIndex + 1
        )
      } catch (ioException: IOException) {
        LoadResult.Error(ioException)
      } catch (clientRequestException: ClientRequestException) {
        LoadResult.Error(clientRequestException)
      } catch (serverResponseException: ServerResponseException) {
        LoadResult.Error(serverResponseException)
      }
    }
    ```
    
  + Change `HttpException` from Retrofit to `ClientRequestException` from Ktor, then modify getErrorResponseWrapper() method
    ```kotlin
    private suspend fun getErrorResponseWrapper(
        exception: ClientRequestException): ResponseWrapper.Error {
      return ResponseWrapper.Error(
          exception.response.status.value,
          exception.response.body())
    }
    ```

+ Clean up unnecessary codes and remove retrofit dependencies
