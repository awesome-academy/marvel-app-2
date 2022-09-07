package com.nguyennhatminh614.marvelapp.screen.character

import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.ComicDTO
import com.nguyennhatminh614.marvelapp.data.model.EventDTO
import com.nguyennhatminh614.marvelapp.data.model.SeriesDTO
import com.nguyennhatminh614.marvelapp.data.model.StoriesDTO
import com.nguyennhatminh614.marvelapp.data.repository.CharacterRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.character.CharacterLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.CharacterDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.character.CharacterRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.DetailCharacterFragmentBinding
import com.nguyennhatminh614.marvelapp.util.DTOItemAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl
import com.nguyennhatminh614.marvelapp.util.extensions.navigateToDirectLink

class DetailCharacterFragment :
    BaseFragment<DetailCharacterFragmentBinding>(DetailCharacterFragmentBinding::inflate) {

    private var character: Character? = null

    private val characterPresenter by lazy {
        CharacterPresenter.getInstance(
            CharacterRepository.getInstance(
                CharacterLocalDataSource.getInstance(
                    CharacterDAOImpl.getInstance(
                        LocalDatabase.getInstance(context)
                    )
                ),
                CharacterRemoteDataSource.getInstance()
            )
        )
    }

    private fun setRecyclerViewDetailStories(it: Character) {
        viewBinding.recyclerViewDetailStories.adapter =
            DTOItemAdapter<StoriesDTO>().apply {
                updateDTOAdapter(it.storiesList) {
                    /* TODO implement later */
                }
            }
    }

    private fun setRecyclerViewDetailSeries(it: Character) {
        viewBinding.recyclerViewDetailSeries.adapter =
            DTOItemAdapter<SeriesDTO>().apply {
                updateDTOAdapter(it.seriesList) {
                    /* TODO implement later */
                }
            }

    }

    private fun setRecyclerViewDetailEvent(it: Character) {
        viewBinding.recyclerViewDetailEvent.adapter =
            DTOItemAdapter<EventDTO>().apply {
                updateDTOAdapter(it.eventList) {
                    /* TODO implement later */
                }
            }
    }

    private fun setRecyclerViewDetailComic(it: Character) {
        viewBinding.recyclerViewDetailComic.adapter =
            DTOItemAdapter<ComicDTO>().apply {
                updateDTOAdapter(it.comicList) {
                    /* TODO implement later */
                }
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
        }
    }

    override fun initEvent() {
        character?.let {
            viewBinding.buttonFavorite.setOnClickListener { view ->
                val checkExist = characterPresenter.checkFavoriteItemExist(it)
                checkExist?.apply {
                    if (it.isFavorite && this.not()) {
                        viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                        characterPresenter.addCharacterFavoriteToListLocal(it)
                        it.isFavorite = it.isFavorite.not()
                    }
                    if (it.isFavorite.not() && this) {
                        viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
                        characterPresenter.removeCharacterFavoriteToListLocal(it)
                        it.isFavorite = it.isFavorite.not()
                    }
                }
            }

            viewBinding.textDetailAboutThisCharacter.setOnClickListener { view ->
                navigateToDirectLink(it.detailUrl)
            }

            viewBinding.textWikiAboutThisCharacter.setOnClickListener { view ->
                navigateToDirectLink(it.wikiUrl)
            }

            viewBinding.textComicLinkAboutThisCharacter.setOnClickListener { view ->
                navigateToDirectLink(it.comicLinkUrl)
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
