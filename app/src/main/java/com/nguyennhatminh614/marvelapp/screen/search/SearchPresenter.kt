package com.nguyennhatminh614.marvelapp.screen.search

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.SearchRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class SearchPresenter(
    private val searchRepository: SearchRepository?
) : BasePresenter<SearchContract.View>, SearchContract.Presenter {

    private var view: SearchContract.View? = null

    override fun queryComicName(stringRequest: String) {
        searchRepository?.getRemoteListComicByName(
            stringRequest,
            object : OnResultListener<MutableList<Comic>> {
                override fun onSuccess(data: MutableList<Comic>?) {
                    view?.onSuccessGetListComic(data)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun queryCharacterName(stringRequest: String) {
        searchRepository?.getRemoteListCharacterByName(
            stringRequest,
            object : OnResultListener<MutableList<Character>> {
                override fun onSuccess(data: MutableList<Character>?) {
                    view?.onSuccessGetListCharacter(data)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun queryEventName(stringRequest: String) {
        searchRepository?.getRemoteListEventByName(
            stringRequest,
            object : OnResultListener<MutableList<Event>> {
                override fun onSuccess(data: MutableList<Event>?) {
                    view?.onSuccessGetListEvent(data)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun querySeriesName(stringRequest: String) {
        searchRepository?.getRemoteListSeriesByName(
            stringRequest,
            object : OnResultListener<MutableList<Series>> {
                override fun onSuccess(data: MutableList<Series>?) {
                    view?.onSuccessGetListSeries(data)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun queryCreatorName(stringRequest: String) {
        searchRepository?.getRemoteListCreatorByName(
            stringRequest,
            object : OnResultListener<MutableList<Creator>> {
                override fun onSuccess(data: MutableList<Creator>?) {
                    view?.onSuccessGetListCreator(data)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun onStart() {
        // Not support
    }

    override fun onStop() {
        // Not support
    }

    override fun setView(view: SearchContract.View?) {
        this.view = view
    }

    companion object {
        private var instance: SearchPresenter? = null

        fun getInstance(searchRepository: SearchRepository?) =
            synchronized(this) {
                instance ?: SearchPresenter(searchRepository).also { instance = it }
            }
    }
}
