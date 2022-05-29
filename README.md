# RetrofitToKtor

A movie catalogue application sample to preview movies data and developed with Android Jetpack.

## The Movie DB API

MovieCatalogue uses [The Movie DB API](https://www.themoviedb.org/) to preview all movies and tv shows data. To get your API key, please refer to the [API documentation](https://www.themoviedb.org/documentation/api).

Add your API key and access token to gradle.properties file in the project's root folder.
```gradle
themoviedb_api_key=<your API Key (v3 auth)>
themoviedb_access_token=<your Access Token (v4 auth)>
```

## Demo

https://user-images.githubusercontent.com/53588149/170821249-2d183032-2577-4a5f-b282-9d82ab9281e3.mov

## Ktor migration

Ktor is a multiplatform framework to do asynchronous operations from Jetbrains. It builts from kotlin and supports kotlin Coroutine.
Ktor migration guideline has been created for learning purposes, excluding the unit tests.
+ Refer to [ktor/start](https://github.com/syntialai/RetrofitToKtor/tree/ktor/start) branch for Ktor migration starter code and the guidelines.
+ Refer to [ktor/finished](https://github.com/syntialai/RetrofitToKtor/tree/ktor/finished) branch for the completed code of migration to Ktor.
