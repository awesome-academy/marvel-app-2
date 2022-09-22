package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.FavoriteItem
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class FavoriteRepository(
    private val local : IFavoriteDataSource.Local,
    private val remote: IFavoriteDataSource.Remote
) : IFavoriteDataSource.Local, IFavoriteDataSource.Remote {
    override fun getCharacterListLocal(listener: OnResultListener<MutableList<Character>>) {
        local.getCharacterListLocal(listener)
    }

    override fun getComicListLocal(listener: OnResultListener<MutableList<Comic>>) {
        local.getComicListLocal(listener)
    }

    override fun getSeriesListLocal(listener: OnResultListener<MutableList<Series>>) {
        local.getSeriesListLocal(listener)
    }

    override fun getStoriesListLocal(listener: OnResultListener<MutableList<Stories>>) {
        local.getStoriesListLocal(listener)
    }

    override fun removeItemFromLocal(favoriteItem: FavoriteItem): Boolean {
        return local.removeItemFromLocal(favoriteItem)
    }

    override fun getCharacterListRemoteWithID(id: Int, listener: OnResultListener<Character>) {
        remote.getCharacterListRemoteWithID(id, listener)
    }

    override fun getComicListRemoteWithID(id: Int, listener: OnResultListener<Comic>) {
        remote.getComicListRemoteWithID(id, listener)
    }

    override fun getSeriesListRemoteWithID(id: Int, listener: OnResultListener<Series>) {
        remote.getSeriesListRemoteWithID(id, listener)
    }

    override fun getStoriesListRemoteWithID(id: Int, listener: OnResultListener<Stories>) {
        remote.getStoriesListRemoteWithID(id, listener)
    }

    companion object {
        private var instance: FavoriteRepository? = null

        fun getInstance(local : IFavoriteDataSource.Local, remote: IFavoriteDataSource.Remote) = synchronized(this) {
            instance ?: FavoriteRepository(local, remote).also { instance = it }
        }
    }
}
