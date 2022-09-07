package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
interface ICharacterDataSource {
    interface Local {
        fun getCharacterListLocal(listener: OnResultListener<MutableList<Character>>)
        fun addCharacterFavoriteToListLocal(character: Character)
        fun removeCharacterFavoriteToListLocal(character: Character)
        fun checkFavoriteCharacterExists(character: Character) : Boolean?
    }

    interface Remote {
        fun getCharacterListRemote(listener: OnResultListener<MutableList<Character>>)
    }
}