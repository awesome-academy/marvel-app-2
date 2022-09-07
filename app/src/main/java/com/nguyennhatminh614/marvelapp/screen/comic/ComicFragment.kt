package com.nguyennhatminh614.marvelapp.screen.comic

import android.widget.Toast
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

    private lateinit var adapter: ComicAdapter

    override fun initData() {
        viewBinding.recyclerViewComic.adapter = adapter
    }

    override fun initialize() {
        comicPresenter.setView(this)

        adapter = ComicAdapter().apply {
            registerClickFavoriteItemListener(
                object : OnClickFavoriteItemInterface<Comic> {
                    override fun onFavoriteItem(item: Comic) {
                        val checkExist = comicPresenter.checkExistComic(item)
                        if (checkExist != null && checkExist == false) {
                            comicPresenter.addComicToFavoriteList(item)
                        }
                    }

                    override fun onUnfavoriteItem(item: Comic) {
                        comicPresenter.removeComicFromFavoriteList(item)
                    }
                }
            )

            registerClickItemListener(
                object : OnClickItemInterface<Comic> {
                    override fun onClickItem(item: Comic) {
                        parentFragmentManager.beginTransaction()
                            .replace(
                                R.id.nav_host_fragment_content_base,
                                DetailComicFragment.newInstance(item)
                            )
                            .commit()
                    }
                }
            )
        }
    }

    override fun callData() {
        comicPresenter.getRemoteListComic()
        comicPresenter.getAllFavoriteListLocal()
    }

    override fun onSuccessGetFavoriteItem(listComic: MutableList<Comic>?) {
        listComic?.let { listComicLocal.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateFavoriteComicList(listComicLocal)
        }
    }

    override fun onSuccessGetDataFromRemote(listComic: MutableList<Comic>?) {
        listComic?.let { listComicRemote.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateListComic(listComicRemote)
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = ComicFragment()
    }
}
