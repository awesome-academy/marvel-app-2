package com.nguyennhatminh614.marvelapp.data.repository

import android.content.Context
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class CharacterRepository(
    private val local: ICharacterDataSource.Local?,
    private val remote: ICharacterDataSource.Remote?,
) : ICharacterDataSource.Local, ICharacterDataSource.Remote {

    override fun getCharacterListLocal(context: Context?, listener: OnResultListener<MutableList<Character>>) {
        local?.getCharacterListLocal(context, listener)
    }

    override fun addCharacterFavoriteToListLocal(context: Context?, character: Character) {
        local?.addCharacterFavoriteToListLocal(context, character)
    }

    override fun removeCharacterFavoriteToListLocal(context: Context?, character: Character) {
        local?.removeCharacterFavoriteToListLocal(context, character)
    }

    override fun checkFavoriteCharacterExists(context: Context?, character: Character): Boolean {
        return local?.checkFavoriteCharacterExists(context, character)!!
    }

    override fun getCharacterListRemote(listener: OnResultListener<MutableList<Character>>) {
        remote?.getCharacterListRemote(listener)
    }

    companion object {
        private var instance: CharacterRepository? = null

        fun getInstance(local: ICharacterDataSource.Local?, remote: ICharacterDataSource.Remote?) =
            synchronized(this) {
                instance ?: CharacterRepository(local, remote).also { instance = it }
            }
    }
}
