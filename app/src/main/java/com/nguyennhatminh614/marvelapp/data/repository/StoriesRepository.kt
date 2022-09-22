package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class StoriesRepository(
    private val local: IStoriesDataSource.Local,
    private val remote: IStoriesDataSource.Remote,
) : IStoriesDataSource.Local, IStoriesDataSource.Remote{
    override fun getAllFavoriteListLocal(listener: OnResultListener<MutableList<Stories>>) {
        local.getAllFavoriteListLocal(listener)
    }

    override fun checkExistStories(stories: Stories): Boolean {
        return local.checkExistStories(stories)
    }

    override fun addStoriesToFavoriteList(stories: Stories): Boolean {
        return local.addStoriesToFavoriteList(stories)
    }

    override fun removeStoriesFromFavoriteList(id: Int): Boolean {
        return local.removeStoriesFromFavoriteList(id)
    }

    override fun getRemoteListStories(listener: OnResultListener<MutableList<Stories>>) {
        remote.getRemoteListStories(listener)
    }

    override fun getRemoteListStoriesWithOffset(
        offset: Int,
        listener: OnResultListener<MutableList<Stories>>,
    ) {
        remote.getRemoteListStoriesWithOffset(offset, listener)
    }

    companion object {
        private var instance: StoriesRepository? = null

        fun getInstance(local: IStoriesDataSource.Local, remote:IStoriesDataSource.Remote) =
            synchronized(this) {
                instance ?: StoriesRepository(local, remote).also { instance = it }
            }
    }
}
