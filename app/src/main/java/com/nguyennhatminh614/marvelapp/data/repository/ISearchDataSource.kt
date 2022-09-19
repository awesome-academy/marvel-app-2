package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

interface ISearchDataSource {
    interface Remote {
        fun getRemoteListCharacterByName(
            nameStartWith: String,
            listener: OnResultListener<MutableList<Character>>
        )
        fun getRemoteListCreatorByName(
            nameStartWith: String,
            listener: OnResultListener<MutableList<Creator>>
        )
        fun getRemoteListComicByName(
            nameStartWith: String,
            listener: OnResultListener<MutableList<Comic>>
        )
        fun getRemoteListEventByName(
            nameStartWith: String,
            listener: OnResultListener<MutableList<Event>>
        )
        fun getRemoteListSeriesByName(
            nameStartWith: String,
            listener: OnResultListener<MutableList<Series>>
        )
    }
}
