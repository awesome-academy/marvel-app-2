package com.nguyennhatminh614.marvelapp.screen.favorite

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.FavoriteItem
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.FavoriteRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class FavoritePresenter(
    private val favoriteRepository: FavoriteRepository,
) : BasePresenter<FavoriteContract.View>, FavoriteContract.Presenter {

    private var view: FavoriteContract.View? = null

    override fun getListFavorite() {
        FavoritePresenterUtils.getListCharacterFavorite(view, favoriteRepository)
        FavoritePresenterUtils.getListSeriesFavorite(view, favoriteRepository)
        FavoritePresenterUtils.getListStoriesFavorite(view, favoriteRepository)
        FavoritePresenterUtils.getListComicFavorite(view, favoriteRepository)
    }

    override fun removeItemFromFavoriteList(favoriteItem: FavoriteItem) {
        favoriteRepository.remoteItemFromLocal(favoriteItem)
    }

    override fun getCharacterInfoByID(id: Int) {
        favoriteRepository.getCharacterListRemoteWithID(
            id,
            object : OnResultListener<Character> {
                override fun onSuccess(data: Character?) {
                    if (data != null) {
                        view?.onSuccessGetDetailData(data)
                    }
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun getComicInfoByID(id: Int) {
        favoriteRepository.getComicListRemoteWithID(
            id,
            object : OnResultListener<Comic> {
                override fun onSuccess(data: Comic?) {
                    if (data != null) {
                        view?.onSuccessGetDetailData(data)
                    }
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun getSeriesInfoByID(id: Int) {
        favoriteRepository.getSeriesListRemoteWithID(
            id,
            object : OnResultListener<Series> {
                override fun onSuccess(data: Series?) {
                    if (data != null) {
                        view?.onSuccessGetDetailData(data)
                    }
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun getStoriesInfoByID(id: Int) {
        favoriteRepository.getStoriesListRemoteWithID(
            id,
            object : OnResultListener<Stories> {
                override fun onSuccess(data: Stories?) {
                    if (data != null) {
                        view?.onSuccessGetDetailData(data)
                    }
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun onStart() {
        view?.showLoadingDialog()
        getListFavorite()
        view?.hideLoadingDialog()
    }

    override fun onStop() {
        // Not support
    }

    override fun setView(view: FavoriteContract.View?) {
        this.view = view
    }

    companion object {
        private var instance: FavoritePresenter? = null

        fun getInstance(favoriteRepository: FavoriteRepository) = synchronized(this) {
            instance ?: FavoritePresenter(favoriteRepository).also { instance = it }
        }
    }
}
