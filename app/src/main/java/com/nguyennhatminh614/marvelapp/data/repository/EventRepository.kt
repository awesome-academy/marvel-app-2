package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class EventRepository(
    private val remote: IEventDataSource.Remote?
) : IEventDataSource.Remote {

    override fun getEventListRemote(listener: OnResultListener<MutableList<Event>>) {
        remote?.getEventListRemote(listener)
    }

    companion object {
        private var instance: EventRepository? = null

        fun getInstance(remote: IEventDataSource.Remote?) =
            synchronized(this) {
                instance ?: EventRepository(remote).also { instance = it }
            }
    }
}
