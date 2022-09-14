package com.nguyennhatminh614.marvelapp.screen.creator

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.ComicDTO
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.EventDTO
import com.nguyennhatminh614.marvelapp.data.model.SeriesDTO
import com.nguyennhatminh614.marvelapp.data.model.StoriesDTO
import com.nguyennhatminh614.marvelapp.databinding.FragmentDetailCreatorBinding
import com.nguyennhatminh614.marvelapp.util.DTOItemAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl
import com.nguyennhatminh614.marvelapp.util.extensions.navigateToDirectLink

class DetailCreatorFragment : BaseFragment<FragmentDetailCreatorBinding>(FragmentDetailCreatorBinding::inflate) {

    private var creator: Creator? = null

    override fun initData() {
        creator?.let {
            context?.let { notNullContext ->
                viewBinding.imageCreatorDetail.loadGlideImageFromUrl(
                    notNullContext, it.thumbnailLink,
                    R.drawable.image_creator_default
                )
            }

            viewBinding.textNameCreatorDetail.text = it.name
            viewBinding.recyclerViewDetailComic.adapter =
                DTOItemAdapter<ComicDTO>().apply {
                    updateDTOAdapter(it.comicList) {
                        viewBinding.recyclerViewDetailComic.isVisible = false
                        viewBinding.textRecyclerViewComicNotFound.apply {
                            isVisible = true
                            (viewBinding.textTempSeriesList.layoutParams as? ConstraintLayout.LayoutParams)
                                ?.topToBottom = id
                            requestLayout()
                        }
                    }
                }

            viewBinding.recyclerViewDetailSeries.adapter =
                DTOItemAdapter<SeriesDTO>().apply {
                    updateDTOAdapter(it.seriesList) {
                        viewBinding.recyclerViewDetailSeries.isVisible = false
                        viewBinding.textRecyclerViewSeriesNotFound.apply {
                            isVisible = true
                            (viewBinding.textTempStoriesList.layoutParams as? ConstraintLayout.LayoutParams)
                                ?.topToBottom = id
                            requestLayout()
                        }
                    }
                }

            viewBinding.recyclerViewDetailStories.adapter =
                DTOItemAdapter<StoriesDTO>().apply {
                    updateDTOAdapter(it.storiesList) {
                        viewBinding.recyclerViewDetailStories.isVisible = false
                        viewBinding.textRecyclerViewStoriesNotFound.apply {
                            isVisible = true
                            (viewBinding.textTempEventList.layoutParams as? ConstraintLayout.LayoutParams)
                                ?.topToBottom = id
                            requestLayout()
                        }
                    }
                }


            viewBinding.recyclerViewEventList.adapter =
                DTOItemAdapter<EventDTO>().apply {
                    updateDTOAdapter(it.eventList) {
                        viewBinding.recyclerViewEventList.isVisible = false
                        viewBinding.textRecyclerViewEventNotFound.apply {
                            isVisible = true
                            (viewBinding.textTempMoreInformation.layoutParams as? ConstraintLayout.LayoutParams)
                                ?.topToBottom = id
                            requestLayout()
                        }
                    }
                }
        }
    }

    override fun initEvent() {
        creator?.let {
            viewBinding.textDetailAboutThisCharacter.setOnClickListener { _ ->
                navigateToDirectLink(it.detailLink)
            }
        }
    }

    override fun initialize() {
        creator = arguments?.getParcelable(Constant.DETAIL_ITEM)
    }

    override fun callData() {
        // Not support
    }
}
