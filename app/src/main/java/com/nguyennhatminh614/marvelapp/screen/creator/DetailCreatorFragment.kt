package com.nguyennhatminh614.marvelapp.screen.creator

import androidx.core.view.isVisible
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.ComicDTO
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.EventDTO
import com.nguyennhatminh614.marvelapp.data.model.SeriesDTO
import com.nguyennhatminh614.marvelapp.data.model.StoriesDTO
import com.nguyennhatminh614.marvelapp.databinding.DetailCreatorFragmentBinding
import com.nguyennhatminh614.marvelapp.util.DTOItemAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class DetailCreatorFragment :
    BaseFragment<DetailCreatorFragmentBinding>(DetailCreatorFragmentBinding::inflate) {

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
                        viewBinding.textRecyclerViewComicNotFound.isVisible = true
                    }
                }

            viewBinding.recyclerViewDetailSeries.adapter =
                DTOItemAdapter<SeriesDTO>().apply {
                    updateDTOAdapter(it.seriesList) {
                        viewBinding.recyclerViewDetailSeries.isVisible = false
                        viewBinding.textRecyclerViewSeriesNotFound.isVisible = true
                    }
                }

            viewBinding.recyclerViewDetailStories.adapter =
                DTOItemAdapter<StoriesDTO>().apply {
                    updateDTOAdapter(it.storiesList) {
                        viewBinding.recyclerViewDetailStories.isVisible = false
                        viewBinding.textRecyclerViewStoriesNotFound.isVisible = true
                    }
                }


            viewBinding.recyclerViewEventList.adapter =
                DTOItemAdapter<EventDTO>().apply {
                    updateDTOAdapter(it.eventList) {
                        viewBinding.recyclerViewEventList.isVisible = false
                        viewBinding.textRecyclerViewStoriesNotFound.isVisible = true
                    }
                }
        }
    }

    override fun initialize() {
        // Not support
    }

    override fun callData() {
        // Not support
    }

    companion object {
        fun newInstance(creator: Creator) = DetailCreatorFragment().apply {
            this.creator = creator
        }
    }

    override fun initEvent() {
        viewBinding.textDetailAboutThisCharacter.setOnClickListener {
            /* TODO implement later */
        }
    }
}
