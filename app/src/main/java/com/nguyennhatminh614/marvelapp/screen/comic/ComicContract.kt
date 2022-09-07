package com.nguyennhatminh614.marvelapp.screen.comic

import com.nguyennhatminh614.marvelapp.data.model.Comic

interface ComicContract {
    interface View {
        fun onSuccessGetFavoriteItem(listComic: MutableList<Comic>?)
        fun onSuccessGetDataFromRemote(listComic: MutableList<Comic>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getAllFavoriteListLocal()
        fun checkExistComic(comic: Comic): Boolean?
        fun addComicToFavoriteList(comic: Comic)
        fun removeComicFromFavoriteList(comic: Comic)
        fun getRemoteListComic()
    }
}
