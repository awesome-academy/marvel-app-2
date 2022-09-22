package com.nguyennhatminh614.marvelapp.screen.comic

import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.repository.ComicRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class ComicPresenter(
    private val comicRepository: ComicRepository,
) : BasePresenter<ComicContract.View>, ComicContract.Presenter {

    private var view: ComicContract.View? = null

    override fun getAllFavoriteListLocal() {
        view?.showLoadingDialog()
        comicRepository.getAllFavoriteListLocal(
            object : OnResultListener<MutableList<Comic>> {
                override fun onSuccess(data: MutableList<Comic>?) {
                    view?.onSuccessGetFavoriteItem(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun checkExistComic(comic: Comic): Boolean {
        return comicRepository.checkExistComic(comic)
    }

    override fun addComicToFavoriteList(comic: Comic): Boolean {
        return comicRepository.addComicToFavoriteList(comic)
    }

    override fun removeComicFromFavoriteList(id: Int): Boolean {
        return comicRepository.removeComicFromFavoriteList(id)
    }

    override fun getRemoteListComic() {
        view?.showLoadingDialog()
        comicRepository.getRemoteListComic(
            object : OnResultListener<MutableList<Comic>> {
                override fun onSuccess(data: MutableList<Comic>?) {
                    view?.onSuccessGetDataFromRemote(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun getComicListRemoteWithOffset(offset: Int) {
        view?.showLoadingDialog()
        comicRepository.getRemoteListComicWithOffset(
            offset,
            object : OnResultListener<MutableList<Comic>> {
                override fun onSuccess(data: MutableList<Comic>?) {
                    view?.onSuccessGetDataWithOffsetFromRemote(data)
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
        //Not support
    }

    override fun setView(view: ComicContract.View?) {
        this.view = view
    }

    companion object {
        private var instance: ComicPresenter? = null

        fun getInstance(comicRepository: ComicRepository) =
            synchronized(this) {
                instance ?: ComicPresenter(comicRepository).also { instance = it }
            }
    }
}
