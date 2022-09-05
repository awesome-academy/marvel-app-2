package com.nguyennhatminh614.marvelapp.screen.creator

import com.nguyennhatminh614.marvelapp.data.model.Creator

class CreatorContract {
    interface View {
        fun onSuccess(listCreator: MutableList<Creator>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getCreatorListRemote()
    }
}
