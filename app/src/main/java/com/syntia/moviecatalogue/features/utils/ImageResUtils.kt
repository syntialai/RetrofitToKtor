package com.syntia.moviecatalogue.features.utils

import com.syntia.moviecatalogue.R
import com.syntia.moviecatalogue.base.utils.ImageUtils

object ImageResUtils {

  fun getPlaceholderResourceId(
      placeholderType: Int = ImageUtils.PLACEHOLDER_TYPE_POSTER) = when (placeholderType) {
    ImageUtils.PLACEHOLDER_TYPE_POSTER -> R.drawable.drawable_placeholder_poster
    ImageUtils.PLACEHOLDER_TYPE_CAST -> R.drawable.drawable_placeholder_person
    else -> -1
  }
}