package com.nguyennhatminh614.marvelapp.screen.character

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.CharacterRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class CharacterPresenter(
    private val characterRepository: CharacterRepository,
) : BasePresenter<CharacterContract.View>, CharacterContract.Presenter {

    private var view: CharacterContract.View? = null

    override fun getCharacterListFromLocal() {
        view?.showLoadingDialog()
        characterRepository.getCharacterListLocal(object :
            OnResultListener<MutableList<Character>> {
            override fun onSuccess(data: MutableList<Character>?) {
                view?.onSuccessGetFavoriteItem(data)
                view?.hideLoadingDialog()
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
                view?.hideLoadingDialog()
            }
        })
    }

    override fun checkFavoriteItemExist(character: Character): Boolean {
        return characterRepository.checkFavoriteCharacterExists(character)
    }

    override fun addCharacterFavoriteToListLocal(character: Character) : Boolean {
        return characterRepository.addCharacterFavoriteToListLocal(character)
    }

    override fun removeCharacterFavoriteToListLocal(id: Int) : Boolean {
        return characterRepository.removeCharacterFavoriteToListLocal(id)
    }

    override fun getCharacterListRemote() {
        view?.showLoadingDialog()
        characterRepository.getCharacterListRemote(object :
            OnResultListener<MutableList<Character>> {
            override fun onSuccess(data: MutableList<Character>?) {
                view?.onSuccessGetDataFromRemote(data)
                view?.hideLoadingDialog()
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
                view?.hideLoadingDialog()
            }
        })
    }

    override fun getCharacterListRemoteWithOffset(offset: Int) {
        view?.showLoadingDialog()
        characterRepository.getCharacterListRemoteWithOffset(offset,
            object : OnResultListener<MutableList<Character>> {
                override fun onSuccess(data: MutableList<Character>?) {
                    view?.onSuccessGetDataWithOffsetFromRemote(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
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
