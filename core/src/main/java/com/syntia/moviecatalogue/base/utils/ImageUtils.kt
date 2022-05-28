package com.syntia.moviecatalogue.base.utils

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide

object ImageUtils {

  private const val IMAGE_API_URL = "https://image.tmdb.org/t/p/w500"

  const val PLACEHOLDER_TYPE_POSTER = 0
  const val PLACEHOLDER_TYPE_CAST = 1

  fun loadImage(context: Context, imagePath: String?, imageView: ImageView,
      @DrawableRes placeholder: Int) {
    Glide.with(context).load("$IMAGE_API_URL$imagePath").placeholder(placeholder).into(
        imageView).onLoadFailed(AppCompatResources.getDrawable(context, placeholder))
  }
}