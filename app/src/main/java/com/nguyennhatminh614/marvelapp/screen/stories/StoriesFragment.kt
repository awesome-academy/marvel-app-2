package com.nguyennhatminh614.marvelapp.screen.stories

import android.widget.Toast
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.StoriesRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.StoriesDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.stories.StoriesLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.stories.StoriesRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerStoriesBinding
import com.nguyennhatminh614.marvelapp.util.OnClickFavoriteItemInterface
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment

class StoriesFragment :
    BaseFragment<FragmentDrawerStoriesBinding>(FragmentDrawerStoriesBinding::inflate),
    StoriesContract.View {

    private val listFavoriteStoriesLocal = mutableListOf<Stories>()
    private val listRemoteStories = mutableListOf<Stories>()

    private val storiesPresenter by lazy {
        StoriesPresenter.getInstance(
            StoriesRepository.getInstance(
                StoriesLocalDataSource.getInstance(
                    StoriesDAOImpl.getInstance(
                        LocalDatabase.getInstance(context)
                    )
                ),
                StoriesRemoteDataSource.getInstance(),
            )
        )
    }

    private val adapter = StoriesAdapter()

    override fun initData() {
        viewBinding.recyclerViewStories.adapter = adapter
    }

    override fun initialize() {
        storiesPresenter.setView(this)
    }

    override fun callData() {
        storiesPresenter.getStoriesListRemote()
        storiesPresenter.getStoriesListFromLocal()
    }

    override fun initEvent() {
        adapter.apply {
            registerOnClickItemInterface(
                object : OnClickItemInterface<Stories> {
                    override fun onClickItem(item: Stories) {
                        parentFragmentManager.beginTransaction()
                            .replace(
                                R.id.nav_host_fragment_content_base,
                                DetailStoriesFragment.newInstance(item)
                            )
                            .commit()
                    }
                }
            )

            registerOnClickFavoriteItemInterface(
                object : OnClickFavoriteItemInterface<Stories> {
                    override fun onFavoriteItem(item: Stories) {
                        val checkExist = storiesPresenter.checkFavoriteItemExist(item)
                        if (checkExist != null && checkExist == false) {
                            storiesPresenter.addStoriesFavoriteToListLocal(item)
                        }
                    }

                    override fun onUnfavoriteItem(item: Stories) {
                        val checkExist = storiesPresenter.checkFavoriteItemExist(item)
                        if (checkExist != null && checkExist == true) {
                            storiesPresenter.removeStoriesFavoriteToListLocal(item)
                        }
                    }
                }
            )
        }
    }

    override fun onSuccessGetFavoriteItem(listStories: MutableList<Stories>?) {
        listStories?.let { listFavoriteStoriesLocal.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateFavoriteItem(listFavoriteStoriesLocal)
        }
    }

    override fun onSuccessGetDataFromRemote(listStories: MutableList<Stories>?) {
        listStories?.let { listRemoteStories.addAll(it) }
        activity?.runOnUiThread {
            adapter.updateDataItem(listRemoteStories)
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = StoriesFragment()
    }
}
