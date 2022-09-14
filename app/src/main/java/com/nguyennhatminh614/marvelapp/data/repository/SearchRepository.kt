package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class SearchRepository(
    private val remote: ISearchDataSource.Remote?
) : ISearchDataSource.Remote {

    override fun getRemoteListCharacterByName(
        nameStartWith: String,
        listener: OnResultListener<MutableList<Character>>
    ) {
        remote?.getRemoteListCharacterByName(nameStartWith, listener)
    }

    override fun getRemoteListCreatorByName(
        nameStartWith: String,
        listener: OnResultListener<MutableList<Creator>>
    ) {
        remote?.getRemoteListCreatorByName(nameStartWith, listener)
    }

    override fun getRemoteListComicByName(
        nameStartWith: String,
        listener: OnResultListener<MutableList<Comic>>
    ) {
        remote?.getRemoteListComicByName(nameStartWith, listener)
    }

    override fun getRemoteListEventByName(
        nameStartWith: String,
        listener: OnResultListener<MutableList<Event>>
    ) {
        remote?.getRemoteListEventByName(nameStartWith, listener)
    }

    override fun getRemoteListSeriesByName(
        nameStartWith: String,
        listener: OnResultListener<MutableList<Series>>
    ) {
        remote?.getRemoteListSeriesByName(nameStartWith, listener)
    }

    companion object {
        private var instance: SearchRepository? = null

        fun getInstance(remote: ISearchDataSource.Remote?) =
            synchronized(this) {
                instance ?: SearchRepository(remote).also { instance = it }
            }
    }
}
