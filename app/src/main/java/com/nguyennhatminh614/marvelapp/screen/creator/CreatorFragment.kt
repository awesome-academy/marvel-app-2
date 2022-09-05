package com.nguyennhatminh614.marvelapp.screen.creator

import android.widget.Toast
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.CreatorRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.creator.CreatorRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerCreatorBinding
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment

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

    private lateinit var adapter: CreatorAdapter

    override fun initData() {
        // Not support
    }

    override fun initialize() {
        creatorPresenter.setView(this)

        adapter = CreatorAdapter().apply {
            registerClickItemListener(
                object : OnClickItemInterface<Creator> {
                    override fun onClickItem(item: Creator) {
                        parentFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.nav_host_fragment_content_base,
                                DetailCreatorFragment.newInstance(item)
                            ).commit()
                    }
                }
            )
        }
    }

    override fun callData() {
        creatorPresenter.getCreatorListRemote()
    }

    override fun onSuccess(listCreator: MutableList<Creator>?) {
        listCreator?.let { listCreatorRemote.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateItemData(listCreatorRemote)
            viewBinding.recyclerView.adapter = adapter
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = CreatorFragment()
    }
}
