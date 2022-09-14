package com.nguyennhatminh614.marvelapp.data.repository.source.local.comic

import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.repository.IComicDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.ComicDAO
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class ComicLocalDataSource(
    private val comicDAO: ComicDAO
) : IComicDataSource.Local {

    override fun getAllFavoriteListLocal(
        listener: OnResultListener<MutableList<Comic>>
    ) {
        val list = mutableListOf<Comic>()
        list.addAll(comicDAO.getAllFavoriteComic())

        if (list.isNotEmpty()) {
            listener.onSuccess(list)
        } else {
            listener.onError(Exception(Constant.EMPTY_DATA_EXCEPTION))
        }
    }

    override fun checkExistComic(comic: Comic): Boolean {
        return comicDAO.checkExistComic(comic)
    }

    override fun addComicToFavoriteList(comic: Comic) {
        comicDAO.addComicToFavoriteList(comic)
    }

    override fun removeComicFromFavoriteList(id: Int) {
        comicDAO.removeComicFromFavoriteList(id)
    }

    companion object {
        private var instance: ComicLocalDataSource? = null

        fun getInstance(comicDAO: ComicDAO) =
            synchronized(this) {
                instance ?: ComicLocalDataSource(comicDAO).also { instance = it }
            }
    }
}
