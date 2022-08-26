package com.nguyennhatminh614.marvelapp.data.repository

import android.content.Context
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
interface ICharacterDataSource {
    interface Local {
        fun getCharacterListLocal(context: Context?, listener: OnResultListener<MutableList<Character>>)
        fun addCharacterFavoriteToListLocal(context: Context?, character: Character)
        fun removeCharacterFavoriteToListLocal(context: Context?, character: Character)
        fun checkFavoriteCharacterExists(context: Context?, character: Character) : Boolean
    }

    interface Remote {
        fun getCharacterListRemote(listener: OnResultListener<MutableList<Character>>)
    }
}
