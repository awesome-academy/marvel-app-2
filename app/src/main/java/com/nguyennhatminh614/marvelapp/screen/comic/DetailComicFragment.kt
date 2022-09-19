package com.nguyennhatminh614.marvelapp.screen.comic

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.repository.ComicRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.comic.ComicLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.ComicDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.comic.ComicRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDetailComicBinding
import com.nguyennhatminh614.marvelapp.util.DetailScreenAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class DetailComicFragment :
    BaseFragment<FragmentDetailComicBinding>(FragmentDetailComicBinding::inflate) {

    private var comic: Comic? = null

    private val detailScreenAdapter = DetailScreenAdapter()

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
                context, it.thumbnailLink,
                R.drawable.image_comic_default
            )

            viewBinding.textNameComic.text = it.title
            viewBinding.textComicDescription.text = it.description
            viewBinding.textPrintPrice.text = "${it.printPrice} $"
            viewBinding.textNumberOfPages.text = "${it.numberOfPages} pages"

            viewBinding.textDetailSeries.text = it.seriesDetail.textDescription
            viewBinding.recyclerViewDetail.adapter = detailScreenAdapter
            detailScreenAdapter.updateDataItem(it.listDetailContent)

            it.isFavorite = comicPresenter.checkExistComic(it)

            if (it.isFavorite) {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
            } else {
                viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
            }
        }
    }

    override fun initEvent() {
        comic?.let {
            viewBinding.buttonFavorite.setOnClickListener { view ->
                if (it.isFavorite) {
                    viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
                    comicPresenter.removeComicFromFavoriteList(it.id)
                } else {
                    viewBinding.buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                    comicPresenter.addComicToFavoriteList(it)
                }
                it.isFavorite = it.isFavorite.not()
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

    override fun initialize() {
        comic = arguments?.getParcelable(Constant.DETAIL_ITEM)
    }

    override fun callData() {
        // Not support
    }

    private fun navigateToDirectLink(urlString: String) {
        findNavController().navigate(R.id.action_nav_detail_comic_to_web_view,
            bundleOf(Constant.DETAIL_ITEM to urlString))
    }

    companion object {
        fun newInstance(comic: Comic) = DetailComicFragment().apply {
            this.comic = comic
        }
    }
}
