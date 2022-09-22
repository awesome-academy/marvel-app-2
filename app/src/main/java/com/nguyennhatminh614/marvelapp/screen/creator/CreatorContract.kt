package com.nguyennhatminh614.marvelapp.screen.creator

import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.util.ILoadingDialog

class CreatorContract {
    interface View : ILoadingDialog {
        fun onSuccess(listCreator: MutableList<Creator>?)
        fun onSuccessGetCreatorOffsetList(listCreator: MutableList<Creator>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getCreatorListRemote()
        fun getCreatorListRemoteWithOffset(offset: Int)
    }
}
