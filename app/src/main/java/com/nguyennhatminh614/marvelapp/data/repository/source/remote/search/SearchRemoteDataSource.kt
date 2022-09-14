package com.nguyennhatminh614.marvelapp.data.repository.source.remote.search

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.ComicEntry
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.CreatorEntry
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.EventEntry
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.SeriesEntry
import com.nguyennhatminh614.marvelapp.data.repository.ISearchDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.character.CharacterRemoteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.comic.ComicRemoteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.creator.CreatorRemoteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.event.EventRemoteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.series.SeriesRemoteDataSource

class SearchRemoteDataSource : ISearchDataSource.Remote {
    override fun getRemoteListComicByName(
        nameStartWith: String,
        listener: OnResultListener<MutableList<Comic>>
    ) {
        GetJsonFromUrl(
            ComicRemoteDataSource.GET_ALL_COMIC,
            ComicEntry.COMIC_ENTITY,
            listener,
            titleQueryToken = nameStartWith,
        )
    }

    override fun getRemoteListCharacterByName(
        nameStartWith: String,
        listener: OnResultListener<MutableList<Character>>
    ) {
        GetJsonFromUrl(
            CharacterRemoteDataSource.GET_ALL_CHARACTER,
            CharacterEntry.CHARACTER_ENTITY,
            listener,
            nameQueryToken = nameStartWith,
        )
    }

    override fun getRemoteListCreatorByName(
        nameStartWith: String,
        listener: OnResultListener<MutableList<Creator>>
    ) {
        GetJsonFromUrl(
            CreatorRemoteDataSource.GET_ALL_CREATOR,
            CreatorEntry.CREATOR_ENTITY,
            listener,
            nameQueryToken = nameStartWith,
        )
    }

    override fun getRemoteListSeriesByName(
        nameStartWith: String,
        listener: OnResultListener<MutableList<Series>>
    ) {
        GetJsonFromUrl(
            SeriesRemoteDataSource.GET_ALL_SERIES,
            SeriesEntry.SERIES_ENTITY,
            listener,
            titleQueryToken = nameStartWith,
        )
    }

    override fun getRemoteListEventByName(
        nameStartWith: String,
        listener: OnResultListener<MutableList<Event>>
    ) {
        GetJsonFromUrl(
            EventRemoteDataSource.GET_ALL_EVENT,
            EventEntry.EVENT_ENTITY,
            listener,
            nameQueryToken = nameStartWith,
        )
    }

    companion object {
        private var instance: SearchRemoteDataSource? = null

        fun getInstance() =
            synchronized(this) {
                instance ?: SearchRemoteDataSource().also { instance = it }
            }
    }
}
