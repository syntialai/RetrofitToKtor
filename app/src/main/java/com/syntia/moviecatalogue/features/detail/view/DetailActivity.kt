package com.syntia.moviecatalogue.features.detail.view

import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.syntia.moviecatalogue.R
import com.syntia.moviecatalogue.base.domain.model.result.ResultWrapper
import com.syntia.moviecatalogue.base.presentation.view.BaseActivity
import com.syntia.moviecatalogue.base.utils.ImageUtils
import com.syntia.moviecatalogue.base.utils.remove
import com.syntia.moviecatalogue.base.utils.show
import com.syntia.moviecatalogue.base.utils.showOrRemove
import com.syntia.moviecatalogue.core.domain.model.detail.CastUiModel
import com.syntia.moviecatalogue.databinding.ActivityDetailBinding
import com.syntia.moviecatalogue.features.detail.adapter.DetailCastAdapter
import com.syntia.moviecatalogue.features.detail.viewmodel.DetailViewModel
import com.syntia.moviecatalogue.features.utils.ImageResUtils
import com.syntia.moviecatalogue.features.utils.Router
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(), View.OnClickListener {

  private val castAdapter by lazy {
    DetailCastAdapter()
  }

  override val viewBindingInflater: (LayoutInflater) -> ActivityDetailBinding
    get() = ActivityDetailBinding::inflate

  override val viewModel: DetailViewModel by viewModel()

  override fun setupViews() {
    binding.apply {
      buttonBackFromDetail.setOnClickListener(this@DetailActivity)
      buttonUpdateFavorite.setOnClickListener(this@DetailActivity)

      with(recyclerViewDetailCasts) {
        layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL,
            false)
        adapter = castAdapter
        setHasFixedSize(false)
      }
    }

    with(intent) {
      val id = getIntExtra(Router.PARAM_ID, -1)
      getStringExtra(Router.PARAM_TYPE)?.let { type ->
        viewModel.setIdAndType(id, type)
      }
    }
  }

  override fun setupObserver() {
    super.setupObserver()

    viewModel.getIsFavoriteItem()
    viewModel.fetchDetails()
    viewModel.fetchCasts()

    viewModel.details.observe(this, { detail ->
      val dateAndTimeOrEpisode = getDateAndTimeOrEpisodeText(detail.releaseOrFirstAirDate,
          detail.language, detail.runtime, detail.episodeCount)
      setDetailMainInfo(detail.title, dateAndTimeOrEpisode, detail.rating, detail.isAdult)
      setImageAndOverview(detail.image, detail.overview)
    })
    viewModel.casts.observe(this, {
      setCasts(it)
    })
    viewModel.loadCasts.observe(this, { loadingState ->
      onLoadCast(loadingState is ResultWrapper.Loading)
      onErrorFetchCast(
          loadingState is ResultWrapper.Error || loadingState is ResultWrapper.NetworkError)
    })
    viewModel.isFavoriteItem.observe(this, {
      setFavoriteButtonColor(it)
    })
  }

  override fun onClick(v: View?) {
    with(binding) {
      when (v) {
        buttonBackFromDetail -> onBackPressed()
        buttonUpdateFavorite -> viewModel.updateFavoriteItem()
        else -> null
      }
    }
  }

  override fun showLoadingState(isLoading: Boolean) {
    binding.spinKitDetailLoad.showOrRemove(isLoading)
  }

  override fun showErrorState(isError: Boolean, message: String?,
      @StringRes defaultMessageId: Int) {
    super.showErrorState(isError, message, defaultMessageId)
    binding.imageViewDetailErrorState.showOrRemove(isError)
  }

  private fun getDateAndTimeOrEpisodeText(date: String, language: String, runtime: String?,
      episode: Int = 0): String {
    val lastText = runtime ?: resources.getQuantityString(R.plurals.episode_text, episode, episode)
    return if (date.isNotBlank()) {
      getString(R.string.detail_info_date_language_and_runtime_or_episode_text, date, language,
          lastText)
    } else {
      getString(R.string.detail_info_language_and_runtime_or_episode_text, language, lastText)
    }
  }

  private fun onLoadCast(isLoading: Boolean) {
    binding.apply {
      spinKitDetailLoad.showOrRemove(isLoading)
      groupDetailCasts.showOrRemove(isLoading.not())
    }
  }

  private fun onErrorFetchCast(isError: Boolean) {
    binding.groupDetailCasts.showOrRemove(isError.not())
  }

  private fun getScaleAnimation() = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f,
      Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f).apply {
    duration = 500
    interpolator = BounceInterpolator()
  }

  private fun setFavoriteButtonColor(isFavorite: Boolean) {
    with(binding.buttonUpdateFavorite) {
      setImageResource(if (isFavorite) {
        R.drawable.ic_favorite_red
      } else {
        R.drawable.ic_favorite_border
      })
      startAnimation(getScaleAnimation())
    }
  }

  private fun setCasts(casts: MutableList<CastUiModel>) {
    castAdapter.submitList(casts)
    binding.groupDetailCasts.showOrRemove(casts.isNotEmpty())
  }

  private fun setDetailMainInfo(title: String, dateAndTimeOrEpisode: String,
      rating: Pair<String, Float>, isAdult: Boolean) {
    binding.layoutDetailMainInfo.apply {
      root.show()

      textViewDetailTitle.text = title
      textViewDetailDateAndTimeOrEpisode.text = dateAndTimeOrEpisode

      textViewDetailRating.text = rating.first

      ratingBarDetail.show()
      ratingBarDetail.rating = rating.second

      chipMovieAndTvItemAdult.showOrRemove(isAdult)
    }
  }

  private fun setImageAndOverview(image: String, overview: String?) {
    binding.apply {
      imageViewDetail.show()
      ImageUtils.loadImage(this@DetailActivity, image, imageViewDetail,
          ImageResUtils.getPlaceholderResourceId())

      overview?.let { overview ->
        if (overview.isNotBlank()) {
          groupDetailOverview.show()
          textViewDetailOverview.text = overview
        }
      } ?: run {
        groupDetailOverview.remove()
      }
    }
  }
}