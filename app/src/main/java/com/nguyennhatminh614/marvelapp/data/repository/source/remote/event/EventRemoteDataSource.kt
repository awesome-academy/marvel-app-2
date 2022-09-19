package com.nguyennhatminh614.marvelapp.data.repository.source.remote.event

import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.EventEntry
import com.nguyennhatminh614.marvelapp.data.repository.IEventDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class EventRemoteDataSource : IEventDataSource.Remote {

    override fun getEventListRemote(listener: OnResultListener<MutableList<Event>>) {
        GetJsonFromUrl(GET_ALL_EVENT, EventEntry.EVENT_ENTITY, listener).callAPI()
    }

    override fun getEventListRemoteWithOffset(
        offset: Int,
        listener: OnResultListener<MutableList<Event>>,
    ) {
        GetJsonFromUrl(GET_ALL_EVENT, EventEntry.EVENT_ENTITY, listener).callAPI(offset = offset)
    }


    companion object {
        const val GET_ALL_EVENT = "/v1/public/events"
        private var instance : EventRemoteDataSource? = null

        fun getInstance() =
            synchronized(this) {
                instance ?: EventRemoteDataSource().also { instance = it }
            }
    }
}
