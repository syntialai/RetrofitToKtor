package com.syntia.moviecatalogue.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.syntia.moviecatalogue.base.presentation.adapter.BaseDiffCallback
import com.syntia.moviecatalogue.base.presentation.adapter.BasePagingDataAdapter
import com.syntia.moviecatalogue.base.utils.ImageUtils
import com.syntia.moviecatalogue.base.utils.remove
import com.syntia.moviecatalogue.core.domain.model.tvshow.TvShowsUiModel
import com.syntia.moviecatalogue.databinding.LayoutMovieAndTvItemBinding
import com.syntia.moviecatalogue.features.utils.ImageResUtils

class HomeTvShowsAdapter(private val onClickListener: (Int) -> Unit) :
    BasePagingDataAdapter<TvShowsUiModel, LayoutMovieAndTvItemBinding>(diffCallback) {

  companion object {
    val diffCallback = object : BaseDiffCallback<TvShowsUiModel>() {
      override fun contentEquality(oldItem: TvShowsUiModel, newItem: TvShowsUiModel): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }

  override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutMovieAndTvItemBinding
    get() = LayoutMovieAndTvItemBinding::inflate

  override fun getViewHolder(binding: LayoutMovieAndTvItemBinding): BaseViewHolder {
    return HomeTvShowsViewHolder(binding)
  }

  inner class HomeTvShowsViewHolder(binding: LayoutMovieAndTvItemBinding) :
      BaseViewHolder(binding) {

    override fun bind(data: TvShowsUiModel) {
      binding.apply {
        textViewMovieAndTvItemTitle.text = data.title
        chipMovieAndTvItemPopularity.text = data.voteAverage

        if (data.releasedYear.isBlank()) {
          chipMovieAndTvItemYearReleased.remove()
        } else {
          chipMovieAndTvItemYearReleased.text = data.releasedYear
        }

        ImageUtils.loadImage(root.context, data.image, imageViewMovieAndTvItem,
            ImageResUtils.getPlaceholderResourceId())

        root.setOnClickListener {
          onClickListener.invoke(data.id)
        }
      }
    }
  }
}