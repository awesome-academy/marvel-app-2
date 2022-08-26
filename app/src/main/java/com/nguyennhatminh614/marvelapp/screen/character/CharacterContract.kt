package com.nguyennhatminh614.marvelapp.screen.character

import android.content.Context
import com.nguyennhatminh614.marvelapp.data.model.Character

interface CharacterContract {
    interface View {
        fun onSuccessGetFavoriteItem(listCharacter: MutableList<Character>?)
        fun onSuccessGetDataFromRemote(listCharacter: MutableList<Character>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getCharacterListFromLocal(context: Context?)
        fun checkFavoriteItemExist(context: Context?, character: Character) : Boolean
        fun addCharacterFavoriteToListLocal(context: Context?, character: Character)
        fun removeCharacterFavoriteToListLocal(context: Context?, character: Character)
        fun getCharacterListRemote()
    }
}
