package com.nguyennhatminh614.marvelapp.screen.homepage

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.CreatorEntry
import com.nguyennhatminh614.marvelapp.data.repository.HomePageRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class HomePagePresenter(
    private val homePageRepository: HomePageRepository
) : BasePresenter<HomePageContract.View>, HomePageContract.Presenter {

    private var view: HomePageContract.View? = null

    override fun getBannerUrlList() {
        view?.showLoadingDialog()
        homePageRepository.getBannerUrlList(
            object : OnResultListener<List<String>> {
                override fun onSuccess(data: List<String>?) {
                    data?.let { view?.onSuccessGetBannerUrlList(it) }
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun getListRemoteByType(title: String) {
        view?.showLoadingDialog()
        when(title) {
            CharacterEntry.CHARACTER_ENTITY -> {
                homePageRepository.getCharacterListRemote(
                    object : OnResultListener<MutableList<Character>> {
                        override fun onSuccess(data: MutableList<Character>?) {
                            data?.let { view?.onSuccessGetListRemote(it.toMutableList(), title) }
                            view?.hideLoadingDialog()
                        }

                        override fun onError(exception: Exception?) {
                            view?.hideLoadingDialog()
                        }
                    }
                )
            }
            CreatorEntry.CREATOR_ENTITY -> {
                homePageRepository.getCreatorListRemote(
                    object : OnResultListener<MutableList<Creator>> {
                        override fun onSuccess(data: MutableList<Creator>?) {
                            data?.let { view?.onSuccessGetListRemote(it.toMutableList(), title) }
                        }

                        override fun onError(exception: Exception?) {
                            // Not support
                        }
                    }
                )
            }
        }
    }

    override fun addItemToFavoriteList(item: Character) {
        homePageRepository.addCharacterItemToFavoriteList(item)
    }

    override fun removeItemFromFavoriteList(id: Int) {
        homePageRepository.removeCharacterItemFromListLocal(id)
    }

    override fun getFavoriteListCharacterLocal() {
        homePageRepository.getFavoriteCharacterListLocal(
            object : OnResultListener<MutableList<Character>> {
                override fun onSuccess(data: MutableList<Character>?) {
                    data?.let { view?.onSuccessGetCharacterFavoriteListLocal(it) }
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }



    override fun onStart() {
        view?.showLoadingDialog()
        getBannerUrlList()
        getFavoriteListCharacterLocal()
        getListRemoteByType(CharacterEntry.CHARACTER_ENTITY)
        getListRemoteByType(CreatorEntry.CREATOR_ENTITY)
        view?.hideLoadingDialog()
    }

    override fun onStop() {
        // Not support
    }

    override fun setView(view: HomePageContract.View?) {
        this.view = view
    }

    companion object {
        private var instance: HomePagePresenter? = null

        fun getInstance(homePageRepository: HomePageRepository) = synchronized(this) {
            instance ?: HomePagePresenter(homePageRepository).also { instance = it }
        }
    }
}
