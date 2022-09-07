package com.nguyennhatminh614.marvelapp.screen.event

import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.repository.EventRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class EventPresenter(
    private val eventRepository: EventRepository
) : BasePresenter<EventContract.View>, EventContract.Presenter {

    private var view: EventContract.View? = null

    override fun getListEventRemote() {
        eventRepository.getEventListRemote(
            object : OnResultListener<MutableList<Event>> {
                override fun onSuccess(data: MutableList<Event>?) {
                    view?.onSuccess(data)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            }
        )
    }

    override fun onStart() {
        // Not support
    }

    override fun onStop() {
        //Not support
    }

    override fun setView(view: EventContract.View?) {
        this.view = view
    }

    companion object {
        private var instance: EventPresenter? = null

        fun getInstance(eventRepository: EventRepository) =
            synchronized(this) {
                instance ?: EventPresenter(eventRepository).also { instance = it }
            }
    }
}
