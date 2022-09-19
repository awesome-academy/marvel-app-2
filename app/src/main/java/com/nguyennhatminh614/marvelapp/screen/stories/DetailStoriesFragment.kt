package com.nguyennhatminh614.marvelapp.screen.stories

import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.StoriesRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.StoriesDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.stories.StoriesLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.stories.StoriesRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDetailStoriesBinding
import com.nguyennhatminh614.marvelapp.util.DetailScreenAdapter
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
            viewBinding.imageStories.loadGlideImageFromUrl(
                context, it.thumbnailLink,
                R.drawable.image_comic_default
            )

            viewBinding.textNameStories.text = it.title
            viewBinding.textStoriesDescription.text = it.description
            viewBinding.recyclerViewDetail.adapter = DetailScreenAdapter().apply {
                updateDataItem(it.listDetailContent)
            }

            it.isFavorite = storiesPresenter.checkFavoriteItemExist(it)

            if (it.isFavorite) {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
            } else {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
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
