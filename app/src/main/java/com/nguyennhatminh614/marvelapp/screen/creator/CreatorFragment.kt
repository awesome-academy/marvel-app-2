package com.nguyennhatminh614.marvelapp.screen.creator

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.CreatorRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.creator.CreatorRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerCreatorBinding
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class CreatorFragment :
    BaseFragment<FragmentDrawerCreatorBinding>(FragmentDrawerCreatorBinding::inflate),
    CreatorContract.View {

    private val listCreatorRemote = mutableListOf<Creator>()

    private val creatorPresenter by lazy {
        CreatorPresenter.getInstance(
            CreatorRepository.getInstance(
                CreatorRemoteDataSource.getInstance()
            )
        )
    }

    private var adapter = CreatorAdapter()

    override fun initData() {
        viewBinding.recyclerView.adapter = adapter
    }

    override fun initialize() {
        creatorPresenter.setView(this)
    }

    override fun callData() {
        creatorPresenter.getCreatorListRemote()
    }

    override fun initEvent() {
        adapter.registerClickItemListener(
            object : OnClickItemInterface<Creator> {
                override fun onClickItem(item: Creator) {
                    findNavController().navigate(R.id.action_nav_creator_to_nav_detail_creator,
                        bundleOf(Constant.DETAIL_ITEM to item))
                }
            }
        )
    }

    override fun onSuccess(listCreator: MutableList<Creator>?) {
        listCreator?.let { listCreatorRemote.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateItemData(listCreatorRemote)
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG_CREATOR_FRAGMENT = "creatorFragment"
        fun newInstance() = CreatorFragment()
    }
}
