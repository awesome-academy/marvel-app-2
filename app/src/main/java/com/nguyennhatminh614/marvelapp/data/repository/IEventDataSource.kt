package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

interface IEventDataSource {
    interface Remote {
        fun getEventListRemote(listener: OnResultListener<MutableList<Event>>)
        fun getEventListRemoteWithOffset(offset: Int, listener: OnResultListener<MutableList<Event>>)
    }
}
