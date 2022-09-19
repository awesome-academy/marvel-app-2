package com.nguyennhatminh614.marvelapp.screen.event

import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.util.ILoadingDialog

interface EventContract {
    interface View : ILoadingDialog {
        fun onSuccess(listEvent : MutableList<Event>?)
        fun onSuccessGetOffsetEventList(listEvent : MutableList<Event>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getListEventRemote()
        fun getListEventRemoteWithOffset(offset: Int)
    }
}
