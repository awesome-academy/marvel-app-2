package com.nguyennhatminh614.marvelapp.screen.character

import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.ComicDTO
import com.nguyennhatminh614.marvelapp.data.model.EventDTO
import com.nguyennhatminh614.marvelapp.data.model.SeriesDTO
import com.nguyennhatminh614.marvelapp.data.model.StoriesDTO
import com.nguyennhatminh614.marvelapp.databinding.DetailCharacterFragmentBinding
import com.nguyennhatminh614.marvelapp.util.DTOItemAdapter
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class DetailCharacterFragment :
    BaseFragment<DetailCharacterFragmentBinding>(DetailCharacterFragmentBinding::inflate) {

    private var character: Character? = null

    override fun initData() {
        character?.let {
            viewBinding.apply {
                context?.let { notNullContext ->
                    imageCharacter.loadGlideImageFromUrl(
                        notNullContext, it.thumbnailLink,
                        R.drawable.character_image
                    )
                }

                textNameCharacter.text = it.name
                textDescription.text = it.description
                recyclerViewDetailComic.adapter =
                    DTOItemAdapter<ComicDTO>().apply {
                        updateDataItem(it.comicList?.toMutableList() ?: ArrayList())
                        registerClickItemInterface(
                            object : OnClickItemInterface<ComicDTO> {
                                override fun onClickItem(item: ComicDTO) {
                                    /* TODO implement later */
                                }
                            }
                        )
                    }
                recyclerViewDetailEvent.adapter =
                    DTOItemAdapter<EventDTO>().apply {
                        updateDataItem(it.eventList?.toMutableList() ?: ArrayList())
                        registerClickItemInterface(
                            object : OnClickItemInterface<EventDTO> {
                                override fun onClickItem(item: EventDTO) {
                                    /* TODO implement later */
                                }

                            }
                        )
                    }
                recyclerViewDetailSeries.adapter =
                    DTOItemAdapter<SeriesDTO>().apply {
                        updateDataItem(it.seriesList?.toMutableList() ?: ArrayList())
                        registerClickItemInterface(
                            object : OnClickItemInterface<SeriesDTO> {
                                override fun onClickItem(item: SeriesDTO) {
                                    /* TODO implement later */
                                }
                            }
                        )
                    }

                recyclerViewDetailStories.adapter =
                    DTOItemAdapter<StoriesDTO>().apply {
                        updateDataItem(it.storiesList?.toMutableList() ?: ArrayList())

                        registerClickItemInterface(
                            object : OnClickItemInterface<StoriesDTO> {
                                override fun onClickItem(item: StoriesDTO) {
                                    /* TODO implement later */
                                }

                            }
                        )
                    }

                textDetailAboutThisCharacter.setOnClickListener {
                    /* TODO implement later */
                }

                textWikiAboutThisCharacter.setOnClickListener {
                    /* TODO implement later */
                }

                textDetailAboutThisCharacter.setOnClickListener {
                    /* TODO implement later */
                }
            }
        }
    }

    override fun initialize() {
        //Not support
    }

    override fun callData() {
        //Not support
    }

    companion object {
        fun newInstance(character: Character) = DetailCharacterFragment().apply {
            this.character = character
        }
    }
}
