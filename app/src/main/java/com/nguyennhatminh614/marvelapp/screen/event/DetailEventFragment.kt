package com.nguyennhatminh614.marvelapp.screen.event

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.databinding.FragmentDetailEventBinding
import com.nguyennhatminh614.marvelapp.util.DetailScreenAdapter
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class DetailEventFragment :
    BaseFragment<FragmentDetailEventBinding>(FragmentDetailEventBinding::inflate) {

    private var event: Event? = null

    override fun initData() {
        event?.let {
            viewBinding.imageEvent.loadGlideImageFromUrl(
                context, it.thumbnailLink,
                R.drawable.image_comic_default
            )

            viewBinding.textNameEvent.text = it.title
            viewBinding.textDescription.text = it.description
            viewBinding.recyclerViewDetail.adapter = DetailScreenAdapter().apply {
                updateDataItem(it.listDetailContent)
            }
        }
    }

    override fun initEvent() {
        event?.let {
            viewBinding.textDetailAboutThisEvent.setOnClickListener { view ->
                navigateToDirectLink(it.detailLink)
            }
        }
    }

    override fun initialize() {
        event = arguments?.getParcelable(Constant.DETAIL_ITEM)
    }

    override fun callData() {
        // Not support
    }

    private fun navigateToDirectLink(urlString: String) {
        findNavController().navigate(R.id.action_nav_detail_event_to_web_view,
            bundleOf(Constant.DETAIL_ITEM to urlString))
    }

    companion object {
        fun newInstance(event: Event) = DetailEventFragment().apply {
            this.event = event
        }
    }
}
