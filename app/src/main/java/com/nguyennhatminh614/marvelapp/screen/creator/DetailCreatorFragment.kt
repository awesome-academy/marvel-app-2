package com.nguyennhatminh614.marvelapp.screen.creator

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.databinding.FragmentDetailCreatorBinding
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class DetailCreatorFragment :
    BaseFragment<FragmentDetailCreatorBinding>(FragmentDetailCreatorBinding::inflate) {

    private var creator: Creator? = null

    override fun initData() {
        creator?.let {
            viewBinding.imageCreatorDetail.loadGlideImageFromUrl(
                context, it.thumbnailLink,
                R.drawable.image_creator_default
            )

            viewBinding.textNameCreatorDetail.text = it.name

        }
    }

    override fun initEvent() {
        creator?.let {
            viewBinding.textDetailAboutThisCharacter.setOnClickListener { _ ->
                navigateToDirectLink(it.detailLink)
            }
        }
    }

    override fun initialize() {
        creator = arguments?.getParcelable(Constant.DETAIL_ITEM)
    }

    override fun callData() {
        // Not support
    }

    private fun navigateToDirectLink(urlString: String) {
        findNavController().navigate(R.id.action_nav_detail_creator_to_web_view,
            bundleOf(Constant.DETAIL_ITEM to urlString))
    }

    companion object {
        fun newInstance(creator: Creator) = DetailCreatorFragment().apply {
            this.creator = creator
        }
    }
}
