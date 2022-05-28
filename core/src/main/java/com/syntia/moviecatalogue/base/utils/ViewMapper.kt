package com.syntia.moviecatalogue.base.utils

import android.view.View
import com.google.android.material.progressindicator.CircularProgressIndicator

fun View.show() {
  this.visibility = View.VISIBLE
}

fun View.remove() {
  this.visibility = View.GONE
}

fun View.hide() {
  this.visibility = View.INVISIBLE
}

fun View.showOrRemove(show: Boolean) {
  if (show) {
    show()
  } else {
    remove()
  }
}

fun CircularProgressIndicator.showView() {
  this.visibility = View.VISIBLE
}