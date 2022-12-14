package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class CharacterRepository(
    private val local: ICharacterDataSource.Local,
    private val remote: ICharacterDataSource.Remote,
) : ICharacterDataSource.Local, ICharacterDataSource.Remote {

    override fun getCharacterListLocal(listener: OnResultListener<MutableList<Character>>) {
        local.getCharacterListLocal(listener)
    }

    override fun addCharacterFavoriteToListLocal(character: Character) : Boolean {
        return local.addCharacterFavoriteToListLocal(character)
    }

    override fun removeCharacterFavoriteToListLocal(id: Int) : Boolean {
        return local.removeCharacterFavoriteToListLocal(id)
    }

    override fun checkFavoriteCharacterExists(character: Character): Boolean {
        return local.checkFavoriteCharacterExists(character)
    }

    override fun getCharacterListRemote(listener: OnResultListener<MutableList<Character>>) {
        remote.getCharacterListRemote(listener)
    }

    override fun getCharacterListRemoteWithOffset(
        offset: Int,
        listener: OnResultListener<MutableList<Character>>,
    ) {
        remote.getCharacterListRemoteWithOffset(offset, listener)
    }

    companion object {
        private var instance: CharacterRepository? = null

        fun getInstance(local: ICharacterDataSource.Local, remote: ICharacterDataSource.Remote) =
            synchronized(this) {
                instance ?: CharacterRepository(local, remote).also { instance = it }
            }
    }
}
