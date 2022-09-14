package com.nguyennhatminh614.marvelapp.screen.comic

import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.repository.ComicRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class ComicPresenter(
    private val comicRepository: ComicRepository
) : BasePresenter<ComicContract.View>, ComicContract.Presenter {

    private var view: ComicContract.View? = null

    override fun getAllFavoriteListLocal() {
        comicRepository.getAllFavoriteListLocal(
            object : OnResultListener<MutableList<Comic>> {
                override fun onSuccess(data: MutableList<Comic>?) {
                    view?.onSuccessGetFavoriteItem(data)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            }
        )
    }

    override fun checkExistComic(comic: Comic): Boolean {
        return comicRepository.checkExistComic(comic) ?: false
    }

    override fun addComicToFavoriteList(comic: Comic) {
        comicRepository.addComicToFavoriteList(comic)
    }

    override fun removeComicFromFavoriteList(id: Int) {
        comicRepository.removeComicFromFavoriteList(id)
    }

    override fun getRemoteListComic() {
        comicRepository.getRemoteListComic(
            object : OnResultListener<MutableList<Comic>> {
                override fun onSuccess(data: MutableList<Comic>?) {
                    view?.onSuccessGetDataFromRemote(data)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            }
        )
    }

    override fun onStart() {
        getAllFavoriteListLocal()
        getRemoteListComic()
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
