package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

interface IStoriesDataSource {
    interface Local {
        fun getAllFavoriteListLocal(listener: OnResultListener<MutableList<Stories>>)
        fun checkExistStories(stories: Stories): Boolean?
        fun addStoriesToFavoriteList(stories: Stories)
        fun removeStoriesFromFavoriteList(id: Int)
    }

    interface Remote {
        fun getRemoteListStories(listener: OnResultListener<MutableList<Stories>>)
        fun getRemoteListStoriesWithOffset(offset: Int, listener: OnResultListener<MutableList<Stories>>)
    }
}
