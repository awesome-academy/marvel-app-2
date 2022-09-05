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
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
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

            viewBinding.recyclerViewDetailComic.adapter =
                DTOItemAdapter<ComicDTO>().apply {
                    checkList(it.comicList?.toMutableList() ?: ArrayList(), {
                        updateDataItem(it)
                        registerClickItemInterface(object : OnClickItemInterface<ComicDTO> {
                            override fun onClickItem(item: ComicDTO) {
                                /* TODO implement later */
                            }
                        })
                    }, {
                        viewBinding.recyclerViewDetailComic.isVisible = false
                        viewBinding.textRecyclerViewComicNotFound.isVisible = true
                    })
                }

            viewBinding.recyclerViewDetailSeries.adapter =
                DTOItemAdapter<SeriesDTO>().apply {
                    checkList(it.seriesList?.toMutableList() ?: ArrayList(), {
                        updateDataItem(it)
                        registerClickItemInterface(object : OnClickItemInterface<SeriesDTO> {
                            override fun onClickItem(item: SeriesDTO) {
                                /* TODO implement later */
                            }
                        })
                    }, {
                        viewBinding.recyclerViewDetailSeries.isVisible = false
                        viewBinding.textRecyclerViewSeriesNotFound.isVisible = true
                    })
                }

            viewBinding.recyclerViewDetailStories.adapter =
                DTOItemAdapter<StoriesDTO>().apply {
                    checkList(it.storiesList?.toMutableList() ?: ArrayList(), {
                        updateDataItem(it)
                        registerClickItemInterface(object : OnClickItemInterface<StoriesDTO> {
                                override fun onClickItem(item: StoriesDTO) {
                                    /* TODO implement later */
                                }
                            })
                    }, {
                        viewBinding.recyclerViewDetailStories.isVisible = false
                        viewBinding.textRecyclerViewStoriesNotFound.isVisible = true
                    })
                }


            viewBinding.recyclerViewEventList.adapter =
                DTOItemAdapter<EventDTO>().apply {
                    checkList(it.eventList?.toMutableList() ?: ArrayList(), {
                        updateDataItem(it)
                        registerClickItemInterface(object : OnClickItemInterface<EventDTO> {
                                override fun onClickItem(item: EventDTO) {
                                    /* TODO implement later */
                                }
                            })
                    }, {
                        viewBinding.recyclerViewEventList.isVisible = false
                        viewBinding.textRecyclerViewStoriesNotFound.isVisible = true
                    })
                }

            viewBinding.textNameCreatorDetail.text = it.name
            viewBinding.textDetailAboutThisCharacter.setOnClickListener {
                /* TODO implement later */
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

    private fun <T> checkList(
        list: MutableList<T>,
        notEmptyListEvent: (MutableList<T>) -> Unit,
        emptyListEvent: () -> Unit
    ) {
        if (list.isNotEmpty()) {
            notEmptyListEvent(list)
        } else {
            emptyListEvent()
        }
    }
}
