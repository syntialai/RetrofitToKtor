package com.syntia.moviecatalogue.base.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DataMapper {

  const val MOVIE = "movie"
  const val TV = "tv"

  private const val YEAR_START_INDEX = 0
  private const val YEAR_END_INDEX = 4

  fun getTitle(title: String?, name: String?) = title ?: name.orEmpty()

  fun getYear(date: String?) = if (date.isNullOrBlank()) {
    ""
  } else {
    date.substring(YEAR_START_INDEX, YEAR_END_INDEX)
  }

  fun getYear(releaseDate: String?, firstAirDate: String?) = releaseDate?.takeIf {
    it.isNotBlank()
  }?.substring(YEAR_START_INDEX, YEAR_END_INDEX) ?: firstAirDate?.takeIf {
    it.isNotBlank()
  }?.substring(YEAR_START_INDEX, YEAR_END_INDEX).orEmpty()

  fun getFormattedDate(releaseDate: String?, firstAirDate: String?) = releaseDate?.takeIf {
    it.isNotBlank()
  }?.formatDate() ?: firstAirDate?.takeIf {
    it.isNotBlank()
  }?.formatDate().orEmpty()

  private fun String.formatDate(stringPattern: String = "yyyy-MM-dd",
      pattern: String = "MMM d, yyyy"): String {
    val date = SimpleDateFormat(stringPattern, Locale.ENGLISH).parse(this)
    return date?.let {
      SimpleDateFormat(pattern, Locale.ENGLISH).format(it)
    }.orEmpty()
  }
}