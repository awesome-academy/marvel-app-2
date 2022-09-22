package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

interface IComicDataSource {
    interface Local {
        fun getAllFavoriteListLocal(listener: OnResultListener<MutableList<Comic>>)
        fun checkExistComic(comic: Comic) : Boolean
        fun addComicToFavoriteList(comic: Comic): Boolean
        fun removeComicFromFavoriteList(id: Int): Boolean
    }

    interface Remote {
        fun getRemoteListComic(listener: OnResultListener<MutableList<Comic>>)
        fun getRemoteListComicWithOffset(offset: Int, listener: OnResultListener<MutableList<Comic>>)
    }
}
