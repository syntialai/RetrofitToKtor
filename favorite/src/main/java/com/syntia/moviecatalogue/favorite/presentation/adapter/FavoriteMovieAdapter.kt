package com.syntia.moviecatalogue.favorite.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.syntia.moviecatalogue.base.presentation.adapter.BaseDiffCallback
import com.syntia.moviecatalogue.base.presentation.adapter.BasePagingDataAdapter
import com.syntia.moviecatalogue.base.utils.ImageUtils
import com.syntia.moviecatalogue.base.utils.remove
import com.syntia.moviecatalogue.base.utils.showOrRemove
import com.syntia.moviecatalogue.core.domain.model.movie.MovieUiModel
import com.syntia.moviecatalogue.databinding.LayoutMovieAndTvItemBinding
import com.syntia.moviecatalogue.features.utils.ImageResUtils

class FavoriteMovieAdapter(private val onClickListener: (Int) -> Unit) :
    BasePagingDataAdapter<MovieUiModel, LayoutMovieAndTvItemBinding>(diffCallback) {

  companion object {
    val diffCallback = object : BaseDiffCallback<MovieUiModel>() {
      override fun contentEquality(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return oldItem == newItem
      }

      override fun itemEquality(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }

  override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutMovieAndTvItemBinding
    get() = LayoutMovieAndTvItemBinding::inflate

  override fun getViewHolder(binding: LayoutMovieAndTvItemBinding): BaseViewHolder {
    return FavoriteMovieViewHolder(binding)
  }

  inner class FavoriteMovieViewHolder(binding: LayoutMovieAndTvItemBinding) :
      BaseViewHolder(binding) {

    override fun bind(data: MovieUiModel) {
      binding.apply {
        textViewMovieAndTvItemTitle.text = data.title

        chipMovieAndTvItemAdult.showOrRemove(data.adult)
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