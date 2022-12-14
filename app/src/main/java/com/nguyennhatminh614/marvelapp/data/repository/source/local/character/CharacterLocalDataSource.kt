package com.nguyennhatminh614.marvelapp.data.repository.source.local.character

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.ICharacterDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.CharacterDAO
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class CharacterLocalDataSource(
    private val characterDAO: CharacterDAO,
) : ICharacterDataSource.Local {

    override fun getCharacterListLocal(listener: OnResultListener<MutableList<Character>>) {
        val list = ArrayList<Character>()
        list.addAll(characterDAO.getAllFavoriteCharacter())

        if (list.isNotEmpty()) {
            listener.onSuccess(list)
        } else {
            listener.onError(Exception(Constant.EMPTY_DATA_EXCEPTION))
        }
    }

    override fun addCharacterFavoriteToListLocal(character: Character) : Boolean {
        return characterDAO.addFavoriteNewCharacter(character)
    }

    override fun removeCharacterFavoriteToListLocal(id: Int) : Boolean {
        return characterDAO.removeFavoriteCharacter(id)
    }

    override fun checkFavoriteCharacterExists(character: Character): Boolean {
        return characterDAO.checkExistsCharacter(character)
    }

    companion object {
        private var instance: CharacterLocalDataSource? = null

        fun getInstance(characterDAO: CharacterDAO) = synchronized(this) {
            instance ?: CharacterLocalDataSource(characterDAO).also { instance = it }
        }
    }
}
