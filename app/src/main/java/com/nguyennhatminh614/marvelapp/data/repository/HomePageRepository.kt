package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class HomePageRepository(
    private val local: IHomepageDataSource.Local?,
    private val remote: IHomepageDataSource.Remote?
) : IHomepageDataSource.Local, IHomepageDataSource.Remote{

    override fun getBannerUrlList(listener: OnResultListener<List<String>>) {
        local?.getBannerUrlList(listener)
    }

    override fun addCharacterItemToFavoriteList(item: Character) {
        local?.addCharacterItemToFavoriteList(item)
    }

    override fun removeCharacterItemFromListLocal(id: Int) {
        local?.removeCharacterItemFromListLocal(id)
    }

    override fun getFavoriteCharacterListLocal(listener: OnResultListener<MutableList<Character>>) {
        local?.getFavoriteCharacterListLocal(listener)
    }

    override fun getCharacterListRemote(listener: OnResultListener<MutableList<Character>>) {
        remote?.getCharacterListRemote(listener)
    }

    override fun getCreatorListRemote(listener: OnResultListener<MutableList<Creator>>) {
        remote?.getCreatorListRemote(listener)
    }

    companion object {
        private var instance: HomePageRepository? = null

        fun getInstance(local: IHomepageDataSource.Local, remote: IHomepageDataSource.Remote) =
            synchronized(this) {
                instance ?: HomePageRepository(local, remote).also { instance = it }
            }
    }
}
