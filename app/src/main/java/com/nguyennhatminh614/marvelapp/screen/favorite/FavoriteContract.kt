package com.nguyennhatminh614.marvelapp.screen.favorite

import com.nguyennhatminh614.marvelapp.data.model.FavoriteItem
import com.nguyennhatminh614.marvelapp.util.ILoadingDialog

interface FavoriteContract {
    interface View : ILoadingDialog {
        fun onSuccessGetFavoriteList(data: MutableList<Any>)
        fun <T> onSuccessGetDetailData(data: T)
        fun onError(exception: Exception)
    }

    interface Presenter {
        fun getListFavorite()
        fun removeItemFromFavoriteList(favoriteItem: FavoriteItem)
        fun getCharacterInfoByID(id: Int)
        fun getComicInfoByID(id: Int)
        fun getSeriesInfoByID(id: Int)
        fun getStoriesInfoByID(id: Int)
    }
}
