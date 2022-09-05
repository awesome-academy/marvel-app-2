package com.nguyennhatminh614.marvelapp.screen.character

import com.nguyennhatminh614.marvelapp.data.model.Character

interface CharacterContract {
    interface View {
        fun onSuccessGetFavoriteItem(listCharacter: MutableList<Character>?)
        fun onSuccessGetDataFromRemote(listCharacter: MutableList<Character>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getCharacterListFromLocal()
        fun checkFavoriteItemExist(character: Character) : Boolean?
        fun addCharacterFavoriteToListLocal(character: Character)
        fun removeCharacterFavoriteToListLocal(character: Character)
        fun getCharacterListRemote()
    }
}
