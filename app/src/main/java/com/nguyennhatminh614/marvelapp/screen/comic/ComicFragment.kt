package com.nguyennhatminh614.marvelapp.screen.comic

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.repository.ComicRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.comic.ComicLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.ComicDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.comic.ComicRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerComicBinding
import com.nguyennhatminh614.marvelapp.util.OnClickFavoriteItemInterface
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class ComicFragment :
    BaseFragment<FragmentDrawerComicBinding>(FragmentDrawerComicBinding::inflate),
    ComicContract.View {

    private val listComicLocal = mutableListOf<Comic>()
    private val listComicRemote = mutableListOf<Comic>()

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

    private var adapter = ComicAdapter()

    override fun initData() {
        comicPresenter.onStart()
        viewBinding.recyclerViewComic.adapter = adapter
    }

    override fun initialize() {
        comicPresenter.setView(this)
    }

    override fun callData() {
        // Not support
    }

    override fun initEvent() {
        adapter.apply {
            registerClickFavoriteItemListener(
                object : OnClickFavoriteItemInterface<Comic> {
                    override fun onFavoriteItem(item: Comic) {
                        comicPresenter.addComicToFavoriteList(item)
                    }

                    override fun onUnfavoriteItem(item: Comic) {
                        comicPresenter.removeComicFromFavoriteList(item.id)
                    }
                }
            )

            registerClickItemListener(
                object : OnClickItemInterface<Comic> {
                    override fun onClickItem(item: Comic) {
                        findNavController().navigate(R.id.action_nav_comic_to_nav_detail_comic,
                            bundleOf(Constant.DETAIL_ITEM to item))
                    }
                }
            )
        }

        viewBinding.recyclerViewComic.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                        if (it.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                            val offset = adapter.itemCount
                            comicPresenter.getComicListRemoteWithOffset(offset)
                        }
                    }
                }
            }
        )
    }

    override fun onSuccessGetFavoriteItem(listComic: MutableList<Comic>?) {
        listComic?.let { listComicLocal.addAll(it) }
        adapter.updateFavoriteComicList(listComicLocal)
    }

    override fun onSuccessGetDataFromRemote(listComic: MutableList<Comic>?) {
        listComic?.let { listComicRemote.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateListComic(listComicRemote)
        }
    }

    override fun onSuccessGetDataWithOffsetFromRemote(listOffset: MutableList<Comic>?) {
        listOffset?.let { listComicRemote.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateListComic(listComicRemote)
        }
    }

    override fun showLoadingDialog() {
        viewBinding.progressBarLoading.isVisible = true
    }

    override fun hideLoadingDialog() {
        viewBinding.progressBarLoading.isVisible = false
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }
}
