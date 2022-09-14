package com.nguyennhatminh614.marvelapp.screen.stories

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
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
import com.nguyennhatminh614.marvelapp.util.constant.Constant

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
        storiesPresenter.onStart()
    }

    override fun initEvent() {
        adapter.apply {
            registerOnClickItemInterface(
                object : OnClickItemInterface<Stories> {
                    override fun onClickItem(item: Stories) {
                        findNavController().navigate(R.id.action_nav_stories_to_nav_detail_stories,
                            bundleOf(Constant.DETAIL_ITEM to item))
                    }
                }
            )

            registerOnClickFavoriteItemInterface(
                object : OnClickFavoriteItemInterface<Stories> {
                    override fun onFavoriteItem(item: Stories) {
                        storiesPresenter.addStoriesFavoriteToListLocal(item)
                    }

                    override fun onUnfavoriteItem(item: Stories) {
                        storiesPresenter.removeStoriesFavoriteToListLocal(item.id)
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
}
