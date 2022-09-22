package com.nguyennhatminh614.marvelapp.screen.character

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.CharacterRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.character.CharacterLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.CharacterDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.character.CharacterRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDetailCharacterBinding
import com.nguyennhatminh614.marvelapp.util.DetailScreenAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class DetailCharacterFragment :
    BaseFragment<FragmentDetailCharacterBinding>(FragmentDetailCharacterBinding::inflate) {

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

    private val detailScreenAdapter = DetailScreenAdapter()

    override fun initData() {
        character?.let {
            viewBinding.imageCharacter.loadGlideImageFromUrl(
                context, it.thumbnailLink,
                R.drawable.character_image
            )

            viewBinding.textNameCharacter.text = it.name
            viewBinding.textDescription.text = it.description

            viewBinding.recyclerViewDetail.adapter = detailScreenAdapter
            detailScreenAdapter.updateDataItem(it.listDetailContent)

            it.isFavorite = characterPresenter.checkFavoriteItemExist(it)

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
                if (it.isFavorite) {
                    viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
                    characterPresenter.removeCharacterFavoriteToListLocal(it.id)
                } else {
                    viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                    characterPresenter.addCharacterFavoriteToListLocal(it)
                }

                it.isFavorite = it.isFavorite.not()
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
        character = arguments?.getParcelable(Constant.DETAIL_ITEM)
    }

    override fun callData() {
        // Not support
    }

    private fun navigateToDirectLink(urlString: String) {
        findNavController().navigate(R.id.action_nav_detail_character_to_web_view,
            bundleOf(Constant.DETAIL_ITEM to urlString))
    }

    companion object {
        fun newInstance(character: Character) = DetailCharacterFragment().apply {
            this.character = character
        }
    }
}
