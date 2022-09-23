package com.nguyennhatminh614.marvelapp.screen.favorite

import com.nguyennhatminh614.marvelapp.data.model.FavoriteItem
import com.nguyennhatminh614.marvelapp.util.ILoadingDialog

interface FavoriteContract {
    interface View : ILoadingDialog {
        fun <T> onSuccessGetFavoriteList(data: MutableList<T>?)
        fun <T> onSuccessGetDetailData(data: T?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun removeItemFromFavoriteList(favoriteItem: FavoriteItem): Boolean
        fun getListCharacterFavorite()
        fun getListSeriesFavorite()
        fun getListStoriesFavorite()
        fun getListComicFavorite()
        fun getCategoryInfoByID(type: String, id: Int)
    }
}
