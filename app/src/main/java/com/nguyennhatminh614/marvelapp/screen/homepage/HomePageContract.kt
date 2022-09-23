package com.nguyennhatminh614.marvelapp.screen.homepage

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.util.ILoadingDialog

interface HomePageContract {
    interface View : ILoadingDialog{
        fun onSuccessGetBannerUrlList(data: List<String>?)
        fun <T : Any> onSuccessGetListRemote(data: MutableList<T>?)
        fun onSuccessGetCharacterFavoriteListLocal(data: MutableList<Character>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getBannerUrlList()
        fun getCharacterListRemote()
        fun getCreatorListRemote()
        fun addItemToFavoriteList(item: Character): Boolean
        fun removeItemFromFavoriteList(id: Int): Boolean
        fun getFavoriteListCharacterLocal()
    }
}
