package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.FavoriteItem
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

interface IFavoriteDataSource {
    interface Local {
        fun getCharacterListLocal(listener: OnResultListener<MutableList<Character>>)
        fun getComicListLocal(listener: OnResultListener<MutableList<Comic>>)
        fun getSeriesListLocal(listener: OnResultListener<MutableList<Series>>)
        fun getStoriesListLocal(listener: OnResultListener<MutableList<Stories>>)
        fun remoteItemFromLocal(favoriteItem: FavoriteItem)
    }

    interface Remote {
        fun getCharacterListRemoteWithID(id: Int, listener: OnResultListener<Character>)
        fun getComicListRemoteWithID(id: Int, listener: OnResultListener<Comic>)
        fun getSeriesListRemoteWithID(id: Int, listener: OnResultListener<Series>)
        fun getStoriesListRemoteWithID(id: Int, listener: OnResultListener<Stories>)
    }
}