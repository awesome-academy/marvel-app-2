package com.nguyennhatminh614.marvelapp.screen.stories

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.CharacterDTO
import com.nguyennhatminh614.marvelapp.data.model.ComicDTO
import com.nguyennhatminh614.marvelapp.data.model.CreatorDTO
import com.nguyennhatminh614.marvelapp.data.model.SeriesDTO
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.StoriesRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.StoriesDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.stories.StoriesLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.stories.StoriesRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDetailStoriesBinding
import com.nguyennhatminh614.marvelapp.util.DTOItemAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class DetailStoriesFragment :
    BaseFragment<FragmentDetailStoriesBinding>(FragmentDetailStoriesBinding::inflate) {

    private var stories: Stories? = null

    private val storiesPresenter by lazy {
        StoriesPresenter.getInstance(
            StoriesRepository.getInstance(
                StoriesLocalDataSource.getInstance(
                    StoriesDAOImpl.getInstance(
                        LocalDatabase.getInstance(context)
                    )
                ),
                StoriesRemoteDataSource.getInstance()
            )
        )
    }

    override fun initData() {

        stories?.let {
            context?.let { notNullContext -> {
                    viewBinding.imageStories.loadGlideImageFromUrl(
                        notNullContext, it.thumbnailLink,
                        R.drawable.image_comic_default
                    )
                }
            }

            viewBinding.textNameStories.text = it.title
            viewBinding.textStoriesDescription.text = it.description

            if (it.isFavorite) {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
            } else {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
            }

            setRecyclerViewCreatorList(it)
            setRecyclerViewCharacterList(it)
            setRecyclerViewSeriesList(it)
            setRecyclerViewComicList(it)
        }
    }

    private fun setRecyclerViewComicList(it: Stories) {
        viewBinding.recyclerViewDetailComicStoriesScreen.adapter =
            DTOItemAdapter<ComicDTO>().apply {
                updateDTOAdapter(it.comicList) {
                    viewBinding.recyclerViewDetailSeriesStoriesScreen.isVisible = false
                    viewBinding.textRecyclerViewComicNotFound.isVisible = true
                }
            }
    }

    private fun setRecyclerViewSeriesList(it: Stories) {
        viewBinding.recyclerViewDetailSeriesStoriesScreen.adapter =
            DTOItemAdapter<SeriesDTO>().apply {
                updateDTOAdapter(it.seriesList) {
                    viewBinding.recyclerViewDetailSeriesStoriesScreen.isVisible = false
                    viewBinding.textRecyclerViewSeriesNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempComicList.layoutParams
                                as? ConstraintLayout.LayoutParams)?.topToBottom = id
                        requestLayout()
                    }
                }
            }
    }

    private fun setRecyclerViewCharacterList(it: Stories) {
        viewBinding.recyclerViewDetailCharactersStoriesScreen.adapter =
            DTOItemAdapter<CharacterDTO>().apply {
                updateDTOAdapter(it.characterList) {
                    viewBinding.recyclerViewDetailCharactersStoriesScreen.isVisible = false
                    viewBinding.textRecyclerViewCharactersNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempSeriesList.layoutParams
                                as? ConstraintLayout.LayoutParams)?.topToBottom = id
                        requestLayout()
                    }
                }
            }
    }

    private fun setRecyclerViewCreatorList(it: Stories) {
        viewBinding.recyclerViewDetailCharactersStoriesScreen.adapter =
            DTOItemAdapter<CreatorDTO>().apply {
                updateDTOAdapter(it.creatorList) {
                    viewBinding.recyclerViewDetailCreatorStoriesScreen.isVisible = false
                    viewBinding.textRecyclerViewCreatorsNotFound.apply {
                        isVisible = true
                        (viewBinding.textTempCharacterList.layoutParams as? ConstraintLayout.LayoutParams)
                            ?.topToBottom = id
                        requestLayout()
                    }
                }
            }
    }

    override fun initEvent() {
        stories?.let {
            viewBinding.buttonFavorite.setOnClickListener { view ->
                if (it.isFavorite) {
                    viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
                    storiesPresenter.removeStoriesFavoriteToListLocal(it.id)
                } else {
                    viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                    storiesPresenter.addStoriesFavoriteToListLocal(it)
                }
                it.isFavorite = it.isFavorite.not()
            }
        }
    }

    override fun initialize() {
        stories = arguments?.getParcelable(Constant.DETAIL_ITEM)
    }

    override fun callData() {
        // Not support
    }
}
