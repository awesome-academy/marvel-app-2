package com.nguyennhatminh614.marvelapp.screen.character

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.util.ILoadingDialog

interface CharacterContract {
    interface View : ILoadingDialog {
        fun onSuccessGetFavoriteItem(listCharacter: MutableList<Character>?)
        fun onSuccessGetDataFromRemote(listCharacter: MutableList<Character>?)
        fun onSuccessGetDataWithOffsetFromRemote(listCharacterOffset: MutableList<Character>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getCharacterListFromLocal()
        fun checkFavoriteItemExist(character: Character) : Boolean
        fun addCharacterFavoriteToListLocal(character: Character) : Boolean
        fun removeCharacterFavoriteToListLocal(id: Int) : Boolean
        fun getCharacterListRemote()
        fun getCharacterListRemoteWithOffset(offset: Int)
    }
}
