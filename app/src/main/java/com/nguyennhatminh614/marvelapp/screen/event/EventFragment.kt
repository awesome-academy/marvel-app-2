package com.nguyennhatminh614.marvelapp.screen.event

import android.widget.Toast
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.repository.EventRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.event.EventRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerEventBinding
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment

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
        viewBinding.recyclerViewEvent.adapter = adapter
    }

    override fun initialize() {
        eventPresenter.setView(this)
    }

    override fun callData() {
        eventPresenter.getListEventRemote()
    }

    override fun initEvent() {
        adapter.apply {
            registerClickItemListener(
                object : OnClickItemInterface<Event> {
                    override fun onClickItem(item: Event) {
                        parentFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.nav_host_fragment_content_base,
                                DetailEventFragment.newInstance(item)
                            )
                            .commit()
                    }
                }
            )
        }
    }

    override fun onSuccess(listEvent: MutableList<Event>?) {
        listEvent?.let { listEventRemote.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateItemdata(listEvent)
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = EventFragment()
    }
}
