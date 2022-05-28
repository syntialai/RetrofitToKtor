package com.syntia.moviecatalogue.base.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BasePagingDataAdapter<T : Any, VB : ViewBinding>(diffCallback: BaseDiffCallback<T>) :
    PagingDataAdapter<T, BasePagingDataAdapter<T, VB>.BaseViewHolder>(diffCallback) {

  abstract val inflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

  abstract fun getViewHolder(binding: VB): BaseViewHolder

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    return getViewHolder(inflater.invoke(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    getItem(position)?.let { model ->
      holder.bind(model)
    }
  }

  abstract inner class BaseViewHolder(protected val binding: VB) :
      RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(data: T)
  }

  fun isEmpty() = itemCount == 0
}