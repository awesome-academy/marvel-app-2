package com.nguyennhatminh614.marvelapp.screen.series

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.CharacterDTO
import com.nguyennhatminh614.marvelapp.data.model.ComicDTO
import com.nguyennhatminh614.marvelapp.data.model.CreatorDTO
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.StoriesDTO
import com.nguyennhatminh614.marvelapp.data.repository.SeriesRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.SeriesDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.series.SeriesLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.series.SeriesRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDetailSeriesBinding
import com.nguyennhatminh614.marvelapp.util.DTOItemAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl
import com.nguyennhatminh614.marvelapp.util.extensions.navigateToDirectLink

class DetailSeriesFragment :
    BaseFragment<FragmentDetailSeriesBinding>(FragmentDetailSeriesBinding::inflate) {

    private var series: Series? = null

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

    private fun setRecyclerViewCreatorAdapter(it: Series) {
        viewBinding.recyclerViewDetailCreator.adapter =
            DTOItemAdapter<CreatorDTO>().apply {
                updateDTOAdapter(it.creatorList) {
                    viewBinding.recyclerViewDetailCreator.isVisible = false
                    viewBinding.textRecyclerViewCreatorsNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempCharacterList.layoutParams as? ConstraintLayout.LayoutParams)
                            ?.topToBottom = id
                        requestLayout()
                    }
                }
            }
    }

    private fun setRecyclerViewComicAdapter(it: Series) {
        viewBinding.recyclerViewDetailComic.adapter =
            DTOItemAdapter<ComicDTO>().apply {
                updateDTOAdapter(it.comicList) {
                    viewBinding.recyclerViewDetailComic.isVisible = false
                    viewBinding.textRecyclerViewComicNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempMoreInformation.layoutParams
                                as? ConstraintLayout.LayoutParams)?.topToBottom = id
                        requestLayout()
                    }
                }
            }
    }

    private fun setRecyclerViewStoriesAdapter(it: Series) {
        viewBinding.recyclerViewDetailStories.adapter =
            DTOItemAdapter<StoriesDTO>().apply {
                updateDTOAdapter(it.storiesList) {
                    viewBinding.recyclerViewDetailStories.isVisible = false
                    viewBinding.textRecyclerViewStoriesNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempComicList.layoutParams
                                as? ConstraintLayout.LayoutParams)?.topToBottom = id
                        requestLayout()
                    }
                }
            }
    }

    private fun setRecyclerViewCharacterAdapter(it: Series) {
        viewBinding.recyclerViewDetailCharacters.adapter =
            DTOItemAdapter<CharacterDTO>().apply {
                updateDTOAdapter(it.characterList) {
                    viewBinding.recyclerViewDetailCharacters.isVisible = false
                    viewBinding.textRecyclerViewCharactersNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempStoriesList.layoutParams
                                as? ConstraintLayout.LayoutParams)?.topToBottom = id
                        requestLayout()
                    }
                }
            }
    }

    override fun initData() {
        series?.let {
            context?.let { notNullContext -> {
                    viewBinding.imageSeries.loadGlideImageFromUrl(
                        notNullContext, it.thumbnailLink,
                        R.drawable.image_comic_default
                    )
                }
            }

            viewBinding.textNameSeries.text = it.title
            viewBinding.textSeriesDescription.text = it.description
            viewBinding.textStartYear.text = it.startYear.toString()
            viewBinding.textEndYear.text = it.endYear.toString()

            setRecyclerViewCreatorAdapter(it)
            setRecyclerViewCharacterAdapter(it)
            setRecyclerViewStoriesAdapter(it)
            setRecyclerViewComicAdapter(it)

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
                    seriesPresenter.removeSeriesFavoriteToListLocal(it)
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
}
