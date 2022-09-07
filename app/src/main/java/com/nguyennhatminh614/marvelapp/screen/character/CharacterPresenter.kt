package com.nguyennhatminh614.marvelapp.screen.character

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.CharacterRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class CharacterPresenter(
    private val characterRepository: CharacterRepository
) : BasePresenter<CharacterContract.View>, CharacterContract.Presenter {

    private var view: CharacterContract.View? = null

    override fun getCharacterListFromLocal() {
        characterRepository.getCharacterListLocal(object :
            OnResultListener<MutableList<Character>> {
            override fun onSuccess(data: MutableList<Character>?) {
                val dataDistinct = data?.distinctBy { it.id }?.toMutableList()
                view?.onSuccessGetFavoriteItem(dataDistinct)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun checkFavoriteItemExist(character: Character) : Boolean? {
        return characterRepository.checkFavoriteCharacterExists(character) ?: false
    }

    override fun addCharacterFavoriteToListLocal(character: Character) {
        characterRepository.addCharacterFavoriteToListLocal(character)
    }

    override fun removeCharacterFavoriteToListLocal(character: Character) {
        characterRepository.removeCharacterFavoriteToListLocal(character)
    }

    override fun getCharacterListRemote() {
        characterRepository.getCharacterListRemote(object :
            OnResultListener<MutableList<Character>> {
            override fun onSuccess(data: MutableList<Character>?) {

                view?.onSuccessGetDataFromRemote(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun onStart() {
        // Not support
    }

    override fun onStop() {
        // Not support
    }

    override fun setView(view: CharacterContract.View?) {
        this.view = view
    }

    companion object {
        private var instance: CharacterPresenter? = null

        fun getInstance(characterRepository: CharacterRepository) =
            synchronized(this) {
                instance ?: CharacterPresenter(characterRepository).also { instance = it }
            }
    }
}
