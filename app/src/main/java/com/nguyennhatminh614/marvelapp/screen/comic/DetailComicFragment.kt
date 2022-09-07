package com.nguyennhatminh614.marvelapp.screen.comic

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.CharacterDTO
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.CreatorDTO
import com.nguyennhatminh614.marvelapp.data.model.EventDTO
import com.nguyennhatminh614.marvelapp.data.model.StoriesDTO
import com.nguyennhatminh614.marvelapp.data.repository.ComicRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.comic.ComicLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.ComicDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.comic.ComicRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.DetailComicFragmentBinding
import com.nguyennhatminh614.marvelapp.util.DTOItemAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl
import com.nguyennhatminh614.marvelapp.util.extensions.navigateToDirectLink

class DetailComicFragment :
    BaseFragment<DetailComicFragmentBinding>(DetailComicFragmentBinding::inflate) {

    private var comic: Comic? = null

    private val comicPresenter by lazy {
        ComicPresenter.getInstance(
            ComicRepository.getInstance(
                ComicLocalDataSource.getInstance(
                    ComicDAOImpl.getInstance(
                        LocalDatabase.getInstance(context)
                    )
                ),
                ComicRemoteDataSource.getInstance()
            )
        )
    }

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

            viewBinding.textDetailSeries.text = it.seriesDetail.textDescription

            if (it.isFavorite) {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
            } else {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
            }
        }
    }

    private fun setRCDetailStories(it: Comic) {
        viewBinding.recyclerViewDetailStories.adapter =
            DTOItemAdapter<StoriesDTO>().apply {
                updateDTOAdapter(it.storiesList) {
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
                updateDTOAdapter(it.eventList) {
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
                updateDTOAdapter(comic.creatorList) {
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
        viewBinding.recyclerViewDetailCharacters.adapter =
            DTOItemAdapter<CharacterDTO>().apply {
                updateDTOAdapter(comic.characterList) {
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

    override fun initEvent() {
        comic?.let {
            viewBinding.buttonFavorite.setOnClickListener { view ->
                val checkExist = comicPresenter.checkExistComic(it)
                checkExist?.apply {
                    if (it.isFavorite && checkExist == false) {
                        viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                        comicPresenter.addComicToFavoriteList(it)
                        it.isFavorite = it.isFavorite.not()
                    }

                    if (it.isFavorite.not() && checkExist == true) {
                        viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
                        comicPresenter.removeComicFromFavoriteList(it)
                        it.isFavorite = it.isFavorite.not()
                    }
                }
            }

            viewBinding.textDetailSeries.apply {
                setOnClickListener { view ->
                    navigateToDirectLink(it.seriesDetail.resourceUrl)
                }
            }
            viewBinding.textDetailAboutThisComic.apply {
                setOnClickListener { view ->
                    navigateToDirectLink(it.comicDetailLink)
                }
            }
        }
    }

    companion object {
        fun newInstance(comic: Comic) = DetailComicFragment().apply {
            this.comic = comic
        }
    }
}
