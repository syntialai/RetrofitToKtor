package com.syntia.moviecatalogue.core.config.api

object ApiPath {

  private const val POPULAR = "popular"

  const val ID = "id"
  const val PAGE = "page"
  const val QUERY = "query"

  const val TRENDING_ALL_WEEK = "trending/all/week"

  const val SEARCH_MULTI = "search/multi"

  const val MOVIE = "movie"
  const val GET_MOVIE_POPULAR = "$MOVIE/$POPULAR"
  const val GET_MOVIE_ID = "$MOVIE/{$ID}"
  const val GET_MOVIE_ID_CREDITS = "$MOVIE/{$ID}/credits"

  private const val TV = "tv"
  const val GET_TV_TOP_POPULAR = "$TV/$POPULAR"
  const val GET_TV_ID = "$TV/{$ID}"
  const val GET_TV_ID_CREDITS = "$TV/{$ID}/credits"
}