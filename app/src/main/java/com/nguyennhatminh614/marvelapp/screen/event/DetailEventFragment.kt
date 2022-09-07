package com.nguyennhatminh614.marvelapp.screen.event

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.CharacterDTO
import com.nguyennhatminh614.marvelapp.data.model.ComicDTO
import com.nguyennhatminh614.marvelapp.data.model.CreatorDTO
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.StoriesDTO
import com.nguyennhatminh614.marvelapp.databinding.DetailEventFragmentBinding
import com.nguyennhatminh614.marvelapp.util.DTOItemAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl
import com.nguyennhatminh614.marvelapp.util.extensions.navigateToDirectLink

class DetailEventFragment :
    BaseFragment<DetailEventFragmentBinding>(DetailEventFragmentBinding::inflate) {

    private var event: Event? = null

    override fun initData() {
        event?.let {
            context?.let { notNullContext ->
                viewBinding.imageEvent.loadGlideImageFromUrl(
                    notNullContext, it.thumbnailLink,
                    R.drawable.image_comic_default
                )
            }

            viewBinding.textNameEvent.text = it.title
            viewBinding.textDescription.text = it.description

            setRecyclerViewCreatorAdapter(it)
            setRecyclerViewCharacterAdapter(it)
            setRecyclerViewStoriesAdapter(it)
            setRecyclerViewComicAdapter(it)
        }
    }

    private fun setRecyclerViewComicAdapter(it: Event) {
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

    private fun setRecyclerViewStoriesAdapter(it: Event) {
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

    private fun setRecyclerViewCharacterAdapter(it: Event) {
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

    private fun setRecyclerViewCreatorAdapter(it: Event) {
        viewBinding.recyclerViewDetailCreator.adapter =
            DTOItemAdapter<CreatorDTO>().apply {
                updateDTOAdapter(it.creatorList) {
                    viewBinding.recyclerViewDetailCreator.isVisible = false
                    viewBinding.textRecyclerViewCreatorsNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempCharacterList.layoutParams
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

    override fun initEvent() {
        event?.let {
            viewBinding.textDetailAboutThisEvent.setOnClickListener { view ->
                navigateToDirectLink(it.detailLink)
            }
        }
    }

    companion object {
        fun newInstance(event: Event) = DetailEventFragment().apply {
            this.event = event
        }
    }
}
