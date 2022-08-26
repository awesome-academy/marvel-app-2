package com.nguyennhatminh614.marvelapp.data.repository.source.local

import android.content.Context
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.ICharacterDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.MyDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class CharacterLocalDataSource : ICharacterDataSource.Local {

    override fun getCharacterListLocal(context: Context?, listener: OnResultListener<MutableList<Character>>) {
        val list = ArrayList<Character>()
        list.addAll(MyDatabase.getInstance(context)?.getAllFavoriteCharacter()!!)

        if (list.isNotEmpty()) {
            listener.onSuccess(list)
        } else {
            listener.onError(Exception(Constant.EMPTY_DATA_EXCEPTION))
        }
    }

    override fun addCharacterFavoriteToListLocal(context: Context?, character: Character) {
        MyDatabase.getInstance(context)?.addFavoriteNewCharacter(character)
    }

    override fun removeCharacterFavoriteToListLocal(context: Context?, character: Character) {
        MyDatabase.getInstance(context)?.removeFavoriteCharacter(character)
    }

    override fun checkFavoriteCharacterExists(context: Context?, character: Character): Boolean {
        return MyDatabase.getInstance(context)?.checkExistsCharacter(character)!!
    }

    companion object {
        private var instance: CharacterLocalDataSource? = null
        fun getInstance() =
            if (instance != null) instance else CharacterLocalDataSource().also { instance = it }
    }
}
