package com.nguyennhatminh614.marvelapp.screen.character

import android.content.Intent
import android.net.Uri
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.ComicDTO
import com.nguyennhatminh614.marvelapp.data.model.EventDTO
import com.nguyennhatminh614.marvelapp.data.model.SeriesDTO
import com.nguyennhatminh614.marvelapp.data.model.StoriesDTO
import com.nguyennhatminh614.marvelapp.data.repository.CharacterRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.character.CharacterLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.character.CharacterRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.DetailCharacterFragmentBinding
import com.nguyennhatminh614.marvelapp.util.DTOItemAdapter
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class DetailCharacterFragment :
    BaseFragment<DetailCharacterFragmentBinding>(DetailCharacterFragmentBinding::inflate) {

    private var character: Character? = null

    private val characterPresenter by lazy {
        CharacterPresenter.getInstance(
            CharacterRepository.getInstance(
                CharacterLocalDataSource.getInstance(),
                CharacterRemoteDataSource.getInstance()
            )
        )
    }

    private fun setRecyclerViewDetailStories(it: Character) {
        viewBinding.recyclerViewDetailStories.adapter =
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
    }

    private fun setRecyclerViewDetailSeries(it: Character) {
        viewBinding.recyclerViewDetailSeries.adapter =
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

    }

    private fun setRecyclerViewDetailEvent(it: Character) {
        viewBinding.recyclerViewDetailEvent.adapter =
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
    }

    private fun setRecyclerViewDetailComic(it: Character) {
        viewBinding.recyclerViewDetailComic.adapter =
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
    }

    override fun initData() {
        character?.let {
            context?.let { notNullContext ->
                viewBinding.imageCharacter.loadGlideImageFromUrl(
                    notNullContext, it.thumbnailLink,
                    R.drawable.character_image
                )
            }

            viewBinding.textNameCharacter.text = it.name
            viewBinding.textDescription.text = it.description

            setRecyclerViewDetailComic(it)
            setRecyclerViewDetailEvent(it)
            setRecyclerViewDetailSeries(it)
            setRecyclerViewDetailStories(it)

            if (it.isFavorite) {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
            } else {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
            }

            viewBinding.buttonFavorite.setOnClickListener { view ->
                it.isFavorite = !it.isFavorite

                if (it.isFavorite) {
                    viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                    if (!characterPresenter.checkFavoriteItemExist(context, it)) {
                        characterPresenter.addCharacterFavoriteToListLocal(context, it)
                    }
                } else {
                    viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
                    characterPresenter.removeCharacterFavoriteToListLocal(context, it)
                }
            }

            viewBinding.textDetailAboutThisCharacter.apply {
                setOnClickListener { view ->
                    if (!text.isNullOrEmpty()) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.detailUrl)))
                    }
                }
            }

            viewBinding.textWikiAboutThisCharacter.apply {
                setOnClickListener { view ->
                    if (!text.isNullOrEmpty()) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.wikiUrl)))
                    }
                }
            }

            viewBinding.textComicLinkAboutThisCharacter.apply {
                setOnClickListener { view ->
                    if (!text.isNullOrEmpty()) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.comicLinkUrl)))
                    }
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
