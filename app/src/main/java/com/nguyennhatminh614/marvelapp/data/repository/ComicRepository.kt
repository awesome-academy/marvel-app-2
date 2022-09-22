package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class ComicRepository(
    private val local: IComicDataSource.Local?,
    private val remote: IComicDataSource.Remote?
): IComicDataSource.Local, IComicDataSource.Remote {

    override fun getAllFavoriteListLocal(
        listener: OnResultListener<MutableList<Comic>>
    ) {
        local?.getAllFavoriteListLocal(listener)
    }

    override fun checkExistComic(comic: Comic): Boolean? {
        return local?.checkExistComic(comic)
    }

    override fun addComicToFavoriteList(comic: Comic) {
        local?.addComicToFavoriteList(comic)
    }

    override fun removeComicFromFavoriteList(id: Int) {
        local?.removeComicFromFavoriteList(id)
    }

    override fun getRemoteListComic(listener: OnResultListener<MutableList<Comic>>) {
        remote?.getRemoteListComic(listener)
    }

    override fun getRemoteListComicWithOffset(
        offset: Int,
        listener: OnResultListener<MutableList<Comic>>,
    ) {
        remote?.getRemoteListComicWithOffset(offset, listener)
    }

    companion object {
        private var instance: ComicRepository? = null

        fun getInstance(local: IComicDataSource.Local?, remote: IComicDataSource.Remote?) =
            synchronized(this) {
                instance ?: ComicRepository(local, remote).also { instance = it }
            }
    }
}
