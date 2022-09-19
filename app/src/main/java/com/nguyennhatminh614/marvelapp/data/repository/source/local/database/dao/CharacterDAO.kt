package com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao

import com.nguyennhatminh614.marvelapp.data.model.Character

interface CharacterDAO {
    fun checkExistsCharacter(character: Character): Boolean
    fun getAllFavoriteCharacter(): ArrayList<Character>
    fun addFavoriteNewCharacter(character: Character)
    fun removeFavoriteCharacter(id: Int)
}
