package com.nguyennhatminh614.marvelapp.screen.homepage

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.util.ILoadingDialog

interface HomePageContract {
    interface View : ILoadingDialog{
        fun onSuccessGetBannerUrlList(data: List<String>)
        fun onSuccessGetListRemote(data: MutableList<Any>, title: String)
        fun onSuccessGetCharacterFavoriteListLocal(data: MutableList<Character>)
        fun onError(exception: Exception)
    }

    interface Presenter {
        fun getBannerUrlList()
        fun getListRemoteByType(title: String)
        fun addItemToFavoriteList(item: Character)
        fun removeItemFromFavoriteList(id: Int)
        fun getFavoriteListCharacterLocal()
    }
}
