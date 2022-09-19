package com.nguyennhatminh614.marvelapp.screen.stories

import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.util.ILoadingDialog

interface StoriesContract {
    interface View : ILoadingDialog{
        fun onSuccessGetFavoriteItem(listStories: MutableList<Stories>?)
        fun onSuccessGetDataFromRemote(listStories: MutableList<Stories>?)
        fun onSuccessGetOffsetDataFromRemote(listStories: MutableList<Stories>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getStoriesListFromLocal()
        fun checkFavoriteItemExist(stories: Stories) : Boolean?
        fun addStoriesFavoriteToListLocal(stories: Stories)
        fun removeStoriesFavoriteToListLocal(id: Int)
        fun getStoriesListRemote()
        fun getStoriesListRemoteWithOffset(offset: Int)
    }
}
