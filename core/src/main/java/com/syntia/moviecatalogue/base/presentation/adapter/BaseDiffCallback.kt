package com.syntia.moviecatalogue.base.presentation.adapter

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffCallback<T> : DiffUtil.ItemCallback<T>() {

  override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
    return itemEquality(oldItem, newItem) ?: contentEquality(oldItem, newItem)
  }

  override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
    return contentEquality(oldItem, newItem)
  }

  abstract fun contentEquality(oldItem: T, newItem: T): Boolean

  open fun itemEquality(oldItem: T, newItem: T): Boolean? = null
}