package com.syntia.moviecatalogue.features.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.syntia.moviecatalogue.features.detail.view.DetailActivity
import com.syntia.moviecatalogue.features.search.view.SearchActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
object Router {

  const val PARAM_ID = "PARAM_ID"
  const val PARAM_TYPE = "PARAM_TYPE"
  private const val LINK_TO_FAVORITE_ITEM_ACTIVITY = "moviecatalogue://favorite"

  fun goToDetails(context: Context, id: Int, type: String) {
    val intent = Intent(context, DetailActivity::class.java).apply {
      putExtra(PARAM_ID, id)
      putExtra(PARAM_TYPE, type)
    }
    context.startActivity(intent)
  }

  fun goToFavorites(context: Context) {
    val favoriteUri = Uri.parse(LINK_TO_FAVORITE_ITEM_ACTIVITY)
    context.startActivity(Intent(Intent.ACTION_VIEW, favoriteUri))
  }

  fun goToSearch(context: Context) {
    val intent = Intent(context, SearchActivity::class.java)
    context.startActivity(intent)
  }
}