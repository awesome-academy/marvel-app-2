package com.nguyennhatminh614.marvelapp.screen.event

import com.nguyennhatminh614.marvelapp.data.model.Event

interface EventContract {
    interface View {
        fun onSuccess(listEvent : MutableList<Event>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getListEventRemote()
    }
}
