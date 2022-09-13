package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class StoriesRepository(
    private val local: IStoriesDataSource.Local?,
    private val remote: IStoriesDataSource.Remote?,
) : IStoriesDataSource.Local, IStoriesDataSource.Remote{
    override fun getAllFavoriteListLocal(listener: OnResultListener<MutableList<Stories>>) {
        local?.getAllFavoriteListLocal(listener)
    }

    override fun checkExistStories(stories: Stories): Boolean? {
        return local?.checkExistStories(stories)
    }

    override fun addStoriesToFavoriteList(stories: Stories) {
        local?.addStoriesToFavoriteList(stories)
    }

    override fun removeStoriesFromFavoriteList(stories: Stories) {
        local?.removeStoriesFromFavoriteList(stories)
    }

    override fun getRemoteListStories(listener: OnResultListener<MutableList<Stories>>) {
        remote?.getRemoteListStories(listener)
    }

    companion object {
        private var instance: StoriesRepository? = null

        fun getInstance(local: IStoriesDataSource.Local?, remote:IStoriesDataSource.Remote?) =
            synchronized(this) {
                instance ?: StoriesRepository(local, remote).also { instance = it }
            }
    }
}
