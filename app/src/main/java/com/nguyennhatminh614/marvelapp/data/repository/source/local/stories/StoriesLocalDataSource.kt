package com.nguyennhatminh614.marvelapp.data.repository.source.local.stories

import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.IStoriesDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.StoriesDAO
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class StoriesLocalDataSource(
    private val storiesDAO: StoriesDAO
) : IStoriesDataSource.Local {

    override fun getAllFavoriteListLocal(
        listener: OnResultListener<MutableList<Stories>>
    ) {
        val list = mutableListOf<Stories>()
        storiesDAO.let { list.addAll(it.getAllFavoriteStories()) }

        if (list.isNotEmpty()) {
            listener.onSuccess(list)
        } else {
            listener.onError(Exception(Constant.EMPTY_DATA_EXCEPTION))
        }
    }

    override fun checkExistStories(stories: Stories): Boolean {
        return storiesDAO.checkExistStories(stories)
    }

    override fun addStoriesToFavoriteList(stories: Stories): Boolean {
        return storiesDAO.addStoriesToFavoriteList(stories)
    }

    override fun removeStoriesFromFavoriteList(id: Int): Boolean {
        return storiesDAO.removeStoriesToFavoriteList(id)
    }

    companion object {
        private var instance: StoriesLocalDataSource? = null

        fun getInstance(storiesDAO: StoriesDAO) =
            synchronized(this) {
                instance ?: StoriesLocalDataSource(storiesDAO).also { instance = it }
            }

    }
}
