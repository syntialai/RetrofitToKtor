package com.syntia.moviecatalogue.features.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syntia.moviecatalogue.base.presentation.adapter.BaseDiffCallback
import com.syntia.moviecatalogue.base.utils.ImageUtils
import com.syntia.moviecatalogue.core.domain.model.detail.CastUiModel
import com.syntia.moviecatalogue.databinding.LayoutCastItemBinding
import com.syntia.moviecatalogue.features.utils.ImageResUtils

class DetailCastAdapter :
    ListAdapter<CastUiModel, DetailCastAdapter.DetailCastViewHolder>(diffCallback) {

  companion object {
    private val diffCallback = object : BaseDiffCallback<CastUiModel>() {
      override fun contentEquality(oldItem: CastUiModel, newItem: CastUiModel): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCastViewHolder {
    return DetailCastViewHolder(
        LayoutCastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: DetailCastViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class DetailCastViewHolder(private val binding: LayoutCastItemBinding) :
      RecyclerView.ViewHolder(binding.root) {

    fun bind(data: CastUiModel) {
      binding.apply {
        textViewCastItemName.text = data.name
        textViewCastItemCharacter.text = data.character

        ImageUtils.loadImage(root.context, data.image, imageViewCastItem,
            ImageResUtils.getPlaceholderResourceId(ImageUtils.PLACEHOLDER_TYPE_CAST))
      }
    }
  }
}