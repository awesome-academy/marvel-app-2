package com.nguyennhatminh614.marvelapp.screen.comic

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.CharacterDTO
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.CreatorDTO
import com.nguyennhatminh614.marvelapp.data.model.DtoItem
import com.nguyennhatminh614.marvelapp.data.model.EventDTO
import com.nguyennhatminh614.marvelapp.data.model.StoriesDTO
import com.nguyennhatminh614.marvelapp.databinding.DetailComicFragmentBinding
import com.nguyennhatminh614.marvelapp.util.DTOItemAdapter
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class DetailComicFragment :
    BaseFragment<DetailComicFragmentBinding>(DetailComicFragmentBinding::inflate) {

    private var comic: Comic? = null

    override fun initData() {
        comic?.let {
            viewBinding.imageComic.loadGlideImageFromUrl(
                requireContext(), it.thumbnailLink,
                R.drawable.image_comic_default
            )
            viewBinding.textNameComic.text = it.title
            viewBinding.textComicDescription.text = it.description
            viewBinding.textPrintPrice.text = "${it.printPrice} $"
            viewBinding.textNumberOfPages.text = "${it.numberOfPages} pages"

            setRCDetailCharacter(it)
            setRCDetailCreator(it)
            setRCDetailEvent(it)
            setRCDetailStories(it)

            viewBinding.textDetailSeries.text = it.seriesDetail?.textDescription

            viewBinding.textDetailSeries.setOnClickListener {
                /* TODO implement later */
            }
            viewBinding.textDetailAboutThisComic.setOnClickListener {
                /* TODO implement later */
            }
            viewBinding.buttonFavorite.setOnClickListener {
                /* TODO implement later */
            }
        }
    }

    private fun setRCDetailStories(it: Comic) {
        viewBinding.recyclerViewDetailStories.adapter =
            DTOItemAdapter<StoriesDTO>().apply {
                updateDTOAdapter(
                    it.storiesList?.toMutableList() ?: ArrayList(),
                    object : OnClickItemInterface<StoriesDTO> {
                        override fun onClickItem(item: StoriesDTO) {
                            /* TODO implement later */
                        }
                    }
                ) {
                    viewBinding.recyclerViewDetailStories.isVisible = false
                    viewBinding.textRecyclerViewStoriesNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempEventList.layoutParams
                                as? ConstraintLayout.LayoutParams)?.topToBottom = id
                        requestLayout()
                    }
                }
            }
    }

    private fun setRCDetailEvent(it: Comic) {
        viewBinding.recyclerViewDetailEvent.adapter =
            DTOItemAdapter<EventDTO>().apply {
                updateDTOAdapter(
                    it.eventList?.toMutableList() ?: ArrayList(),
                    object : OnClickItemInterface<EventDTO> {
                        override fun onClickItem(item: EventDTO) {
                            /* TODO implement later */
                        }
                    }
                ) {
                    viewBinding.recyclerViewDetailEvent.isVisible = false
                    viewBinding.textRecyclerViewEventNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempMoreInformation.layoutParams
                                as? ConstraintLayout.LayoutParams)?.topToBottom = id
                        requestLayout()
                    }
                }
            }
    }

    private fun setRCDetailCreator(comic: Comic) {
        viewBinding.recyclerViewDetailCreators.adapter = DTOItemAdapter<CreatorDTO>()
            .apply {
                updateDTOAdapter(
                    comic.creatorList?.toMutableList() ?: ArrayList(),
                    object : OnClickItemInterface<CreatorDTO> {
                        override fun onClickItem(item: CreatorDTO) {
                            /* TODO implement later */
                        }
                    }
                ) {
                    viewBinding.recyclerViewDetailCreators.isVisible = false
                    viewBinding.textRecyclerViewCreatorsNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempCharacterList.layoutParams
                                as? ConstraintLayout.LayoutParams)?.topToBottom = id
                        requestLayout()
                    }
                }
            }
    }

    private fun setRCDetailCharacter(comic: Comic) {
        viewBinding.recyclerViewDetailCharacters.adapter = DTOItemAdapter<CharacterDTO>()
            .apply {
                updateDTOAdapter(
                    comic.characterList?.toMutableList() ?: ArrayList(),
                    object : OnClickItemInterface<CharacterDTO> {
                        override fun onClickItem(item: CharacterDTO) {
                            /* TODO implement later */
                        }
                    }
                ) {
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

    override fun initialize() {
        //Not support
    }

    override fun callData() {
        //Not support
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

    fun <T : DtoItem> DTOItemAdapter<T>.updateDTOAdapter(
        listItem: MutableList<T>,
        clickItemInterface: OnClickItemInterface<T>,
        emptyListEvent: () -> Unit,
    ) {
        checkList(listItem,
            {
                updateDataItem(it)
                registerClickItemInterface(clickItemInterface)
            },
            {
                emptyListEvent()
            }
        )
    }

    companion object {
        fun newInstance(comic: Comic) = DetailComicFragment().apply {
            this.comic = comic
        }
    }
}
