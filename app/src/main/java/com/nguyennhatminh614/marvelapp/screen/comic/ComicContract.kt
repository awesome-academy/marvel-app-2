package com.nguyennhatminh614.marvelapp.screen.comic

import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.util.ILoadingDialog

interface ComicContract {
    interface View : ILoadingDialog{
        fun onSuccessGetFavoriteItem(listComic: MutableList<Comic>?)
        fun onSuccessGetDataFromRemote(listComic: MutableList<Comic>?)
        fun onSuccessGetDataWithOffsetFromRemote(listOffset: MutableList<Comic>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getAllFavoriteListLocal()
        fun checkExistComic(comic: Comic): Boolean?
        fun addComicToFavoriteList(comic: Comic)
        fun removeComicFromFavoriteList(id: Int)
        fun getRemoteListComic()
        fun getComicListRemoteWithOffset(offset: Int)
    }
}
