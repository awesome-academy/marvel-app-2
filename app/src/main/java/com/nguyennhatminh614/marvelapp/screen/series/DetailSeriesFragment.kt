package com.nguyennhatminh614.marvelapp.screen.series

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.SeriesRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.SeriesDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.series.SeriesLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.series.SeriesRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDetailSeriesBinding
import com.nguyennhatminh614.marvelapp.util.DetailScreenAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class DetailSeriesFragment :
    BaseFragment<FragmentDetailSeriesBinding>(FragmentDetailSeriesBinding::inflate) {

    private var series: Series? = null

    private val detailScreenAdapter = DetailScreenAdapter()

    private val seriesPresenter by lazy {
        SeriesPresenter.getInstance(
            SeriesRepository.getInstance(
                SeriesLocalDataSource.getInstance(
                    SeriesDAOImpl.getInstance(
                        LocalDatabase.getInstance(context)
                    )
                ),
                SeriesRemoteDataSource.getInstance()
            )
        )
    }

    override fun initData() {
        series?.let {
            viewBinding.imageSeries.loadGlideImageFromUrl(
                context, it.thumbnailLink,
                R.drawable.image_comic_default
            )

            viewBinding.textNameSeries.text = it.title
            viewBinding.textSeriesDescription.text = it.description
            viewBinding.textStartYear.text = it.startYear.toString()
            viewBinding.textEndYear.text = it.endYear.toString()

            viewBinding.recyclerViewDetail.adapter = detailScreenAdapter
            detailScreenAdapter.updateDataItem(it.listDetailContent)

            it.isFavorite = seriesPresenter.checkFavoriteItemExist(it) ?: false

            if (it.isFavorite) {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
            } else {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
            }
        }
    }

    override fun initEvent() {
        series?.let {
            viewBinding.textDetailAboutThisSeries.setOnClickListener { view ->
                navigateToDirectLink(it.detailLink)
            }

            viewBinding.buttonFavorite.setOnClickListener { view ->
                if (it.isFavorite) {
                    viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
                    seriesPresenter.removeSeriesFavoriteToListLocal(it.id)
                } else {
                    viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                    seriesPresenter.addSeriesFavoriteToListLocal(it)
                }
                it.isFavorite = it.isFavorite.not()
            }
        }
    }

    override fun initialize() {
        series = arguments?.getParcelable(Constant.DETAIL_ITEM)
    }

    override fun callData() {
        // Not support
    }

    private fun navigateToDirectLink(urlString: String) {
        findNavController().navigate(R.id.action_nav_detail_series_to_web_view,
            bundleOf(Constant.DETAIL_ITEM to urlString))
    }

    companion object {
        fun newInstance(series: Series) = DetailSeriesFragment().apply {
            this.series = series
        }
    }
}
