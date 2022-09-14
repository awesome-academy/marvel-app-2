package com.nguyennhatminh614.marvelapp.screen.character

import com.nguyennhatminh614.marvelapp.data.model.Character

interface CharacterContract {
    interface View {
        fun onSuccessGetFavoriteItem(listCharacter: MutableList<Character>?)
        fun onSuccessGetDataFromRemote(listCharacter: MutableList<Character>?)
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getCharacterListFromLocal()
        fun checkFavoriteItemExist(character: Character) : Boolean?
        fun addCharacterFavoriteToListLocal(character: Character)
        fun removeCharacterFavoriteToListLocal(id: Int)
        fun getCharacterListRemote()
    }
}
