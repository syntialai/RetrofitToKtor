package com.syntia.moviecatalogue.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syntia.moviecatalogue.R
import com.syntia.moviecatalogue.base.presentation.adapter.BaseDiffCallback
import com.syntia.moviecatalogue.base.utils.ImageUtils
import com.syntia.moviecatalogue.core.domain.model.trending.TrendingItemUiModel
import com.syntia.moviecatalogue.databinding.LayoutTrendingItemBinding
import com.syntia.moviecatalogue.features.utils.ImageResUtils
import java.util.Locale

class MainTrendingItemAdapter(private val onClickListener: (Int, String) -> Unit) :
    ListAdapter<TrendingItemUiModel, MainTrendingItemAdapter.MainTrendingItemViewHolder>(
        diffCallback) {

  companion object {
    private val diffCallback = object : BaseDiffCallback<TrendingItemUiModel>() {
      override fun contentEquality(oldItem: TrendingItemUiModel, newItem: TrendingItemUiModel): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTrendingItemViewHolder {
    return MainTrendingItemViewHolder(
        LayoutTrendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: MainTrendingItemViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class MainTrendingItemViewHolder(private val binding: LayoutTrendingItemBinding) :
      RecyclerView.ViewHolder(binding.root) {

    fun bind(data: TrendingItemUiModel) {
      binding.apply {
        textViewTrendingItemTitle.text = data.title
        textViewTrendingItemInfo.text = root.context.getString(R.string.trending_item_info_text,
            data.releasedYear, data.type)
        textViewTrendingItemPopularity.text = data.voteAverage

        ImageUtils.loadImage(root.context, data.image, imageViewTrendingItem,
            ImageResUtils.getPlaceholderResourceId())

        imageViewTrendingItem.setOnClickListener {
          onClickListener.invoke(data.id, data.type.toLowerCase(Locale.ROOT))
        }
      }
    }
  }
}