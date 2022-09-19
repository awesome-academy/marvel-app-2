package com.nguyennhatminh614.marvelapp.data.repository.source.remote.stories

import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.model.StoriesEntry
import com.nguyennhatminh614.marvelapp.data.repository.IStoriesDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class StoriesRemoteDataSource : IStoriesDataSource.Remote {
    override fun getRemoteListStories(listener: OnResultListener<MutableList<Stories>>) {
        GetJsonFromUrl(GET_ALL_STORIES, StoriesEntry.STORIES_ENTITY, listener).callAPI()
    }

    override fun getRemoteListStoriesWithOffset(
        offset: Int,
        listener: OnResultListener<MutableList<Stories>>,
    ) {
        GetJsonFromUrl(GET_ALL_STORIES,
            StoriesEntry.STORIES_ENTITY,
            listener).callAPI(offset = offset)
    }

    companion object {
        const val GET_ALL_STORIES = "/v1/public/stories"
        private var instance: StoriesRemoteDataSource? = null

        fun getInstance() =
            synchronized(this) {
                instance ?: StoriesRemoteDataSource().also { instance = it }
            }
    }
}
