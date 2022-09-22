package com.nguyennhatminh614.marvelapp.screen.homepage

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.HomePageRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class HomePagePresenter(
    private val homePageRepository: HomePageRepository,
) : BasePresenter<HomePageContract.View>, HomePageContract.Presenter {

    private var view: HomePageContract.View? = null

    override fun getBannerUrlList() {
        view?.showLoadingDialog()
        homePageRepository.getBannerUrlList(
            object : OnResultListener<List<String>> {
                override fun onSuccess(data: List<String>?) {
                    view?.onSuccessGetBannerUrlList(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun getCharacterListRemote() {
        view?.showLoadingDialog()
        homePageRepository.getCharacterListRemote(
            object : OnResultListener<MutableList<Character>> {
                override fun onSuccess(data: MutableList<Character>?) {
                    view?.onSuccessGetListRemote(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun getCreatorListRemote() {
        view?.showLoadingDialog()
        homePageRepository.getCreatorListRemote(
            object : OnResultListener<MutableList<Creator>> {
                override fun onSuccess(data: MutableList<Creator>?) {
                    view?.onSuccessGetListRemote(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun addItemToFavoriteList(item: Character): Boolean {
        return homePageRepository.addCharacterItemToFavoriteList(item)
    }

    override fun removeItemFromFavoriteList(id: Int): Boolean {
        return homePageRepository.removeCharacterItemFromListLocal(id)
    }

    override fun getFavoriteListCharacterLocal() {
        view?.showLoadingDialog()
        homePageRepository.getFavoriteCharacterListLocal(
            object : OnResultListener<MutableList<Character>> {
                override fun onSuccess(data: MutableList<Character>?) {
                    view?.onSuccessGetCharacterFavoriteListLocal(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun onStart() {
        // Not support
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
