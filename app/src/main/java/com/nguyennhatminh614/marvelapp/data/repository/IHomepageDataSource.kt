package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

interface IHomepageDataSource {
    interface Local {
        fun getBannerUrlList(listener: OnResultListener<List<String>>)
        fun addCharacterItemToFavoriteList(item: Character) : Boolean
        fun removeCharacterItemFromListLocal(id: Int) : Boolean
        fun getFavoriteCharacterListLocal(listener: OnResultListener<MutableList<Character>>)
    }

    interface Remote {
        fun getCharacterListRemote(listener: OnResultListener<MutableList<Character>>)
        fun getCreatorListRemote(listener: OnResultListener<MutableList<Creator>>)
    }
}
