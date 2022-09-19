package com.nguyennhatminh614.marvelapp.data.repository.source.remote.homepage

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.CreatorEntry
import com.nguyennhatminh614.marvelapp.data.repository.IHomepageDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.character.CharacterRemoteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.creator.CreatorRemoteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

const val LIMIT_ITEM = 3

class HomepageRemoteDataSource : IHomepageDataSource.Remote {

    override fun getCharacterListRemote(listener: OnResultListener<MutableList<Character>>) {
        GetJsonFromUrl(
            CharacterRemoteDataSource.GET_ALL_CHARACTER,
            CharacterEntry.CHARACTER_ENTITY,
            listener).callAPI(limit = LIMIT_ITEM)
    }

    override fun getCreatorListRemote(listener: OnResultListener<MutableList<Creator>>) {
        GetJsonFromUrl(
            CreatorRemoteDataSource.GET_ALL_CREATOR,
            CreatorEntry.CREATOR_ENTITY,
            listener,
        ).callAPI(limit = LIMIT_ITEM)
    }

    companion object {
        private var instance: HomepageRemoteDataSource? = null

        fun getInstance() = synchronized(this) {
            instance ?: HomepageRemoteDataSource().also { instance = it }
        }
    }
}
