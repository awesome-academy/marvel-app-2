package com.nguyennhatminh614.marvelapp.data.repository.source.remote.character

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.repository.ICharacterDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class CharacterRemoteDataSource : ICharacterDataSource.Remote {

    override fun getCharacterListRemote(listener: OnResultListener<MutableList<Character>>) {
        GetJsonFromUrl(GET_ALL_CHARACTER, CharacterEntry.CHARACTER_ENTITY, listener)
    }

    companion object {
        const val GET_ALL_CHARACTER = "/v1/public/characters"
        private var instance: CharacterRemoteDataSource? = null
        fun getInstance() =
            if (instance != null) instance else CharacterRemoteDataSource().also { instance = it }
    }
}
