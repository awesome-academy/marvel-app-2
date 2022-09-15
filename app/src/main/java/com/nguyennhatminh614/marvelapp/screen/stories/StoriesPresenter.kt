package com.nguyennhatminh614.marvelapp.screen.stories

import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.StoriesRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class StoriesPresenter(
    private val storiesRepository: StoriesRepository
) : BasePresenter<StoriesContract.View>, StoriesContract.Presenter {

    private var view: StoriesContract.View? = null

    override fun getStoriesListFromLocal() {
        storiesRepository.getAllFavoriteListLocal(
            object : OnResultListener<MutableList<Stories>> {
                override fun onSuccess(data: MutableList<Stories>?) {
                    view?.onSuccessGetFavoriteItem(data)
                }

                override fun onError(exception: Exception?) {
                    //Not support
                }
            }
        )
    }

    override fun checkFavoriteItemExist(stories: Stories): Boolean? {
        return storiesRepository.checkExistStories(stories) ?: false
    }

    override fun addStoriesFavoriteToListLocal(stories: Stories) {
        storiesRepository.addStoriesToFavoriteList(stories)
    }

    override fun removeStoriesFavoriteToListLocal(stories: Stories) {
        storiesRepository.removeStoriesFromFavoriteList(stories)
    }

    override fun getStoriesListRemote() {
        storiesRepository.getRemoteListStories(
            object : OnResultListener<MutableList<Stories>> {
                override fun onSuccess(data: MutableList<Stories>?) {
                    view?.onSuccessGetDataFromRemote(data)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }

            }
        )
    }

    override fun onStart() {
        getStoriesListFromLocal()
        getStoriesListRemote()
    }

    override fun onStop() {
        //Not support
    }

    override fun setView(view: StoriesContract.View?) {
        this.view = view
    }

    companion object {
        private var instance: StoriesPresenter? = null

        fun getInstance(storiesRepository: StoriesRepository) =
            synchronized(this) {
                instance ?: StoriesPresenter(storiesRepository).also { instance = it }
            }
    }
}
