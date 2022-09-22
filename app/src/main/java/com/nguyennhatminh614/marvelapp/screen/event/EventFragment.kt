package com.nguyennhatminh614.marvelapp.screen.event

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.repository.EventRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.event.EventRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerEventBinding
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class EventFragment : BaseFragment<FragmentDrawerEventBinding>(FragmentDrawerEventBinding::inflate),
    EventContract.View {

    private val listEventRemote = mutableListOf<Event>()

    private val eventPresenter by lazy {
        EventPresenter.getInstance(
            EventRepository.getInstance(
                EventRemoteDataSource.getInstance()
            )
        )
    }

    private var adapter = EventAdapter()

    override fun initData() {
        eventPresenter.getListEventRemote()
        viewBinding.recyclerViewEvent.adapter = adapter
    }

    override fun initialize() {
        eventPresenter.setView(this)
    }

    override fun callData() {
        // Not support
    }

    override fun initEvent() {
        adapter.apply {
            registerClickItemListener(
                object : OnClickItemInterface<Event> {
                    override fun onClickItem(item: Event) {
                        findNavController().navigate(R.id.action_nav_event_to_nav_detail_event,
                            bundleOf(Constant.DETAIL_ITEM to item))
                    }
                }
            )
        }

        viewBinding.recyclerViewEvent.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                        if (it.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                            val offset = adapter.itemCount
                            eventPresenter.getListEventRemoteWithOffset(offset)
                        }
                    }
                }
            }
        )
    }

    override fun onSuccess(listEvent: MutableList<Event>?) {
        listEvent?.let { listEventRemote.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateItemdata(listEventRemote)
        }
    }

    override fun onSuccessGetOffsetEventList(listEvent: MutableList<Event>?) {
        listEvent?.let { listEventRemote.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateItemdata(listEventRemote)
        }
    }

    override fun showLoadingDialog() {
        viewBinding.progressBarLoading.isVisible = true
    }

    override fun hideLoadingDialog() {
        viewBinding.progressBarLoading.isVisible = false
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG_EVENT_FRAGMENT = "eventFragment"
        fun newInstance() = EventFragment()
    }
}
