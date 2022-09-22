package com.nguyennhatminh614.marvelapp.data.repository.source.local.homepage

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.ICharacterDataSource
import com.nguyennhatminh614.marvelapp.data.repository.IHomepageDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class HomePageLocalDataSource(
    private val characterLocal: ICharacterDataSource.Local,
) : IHomepageDataSource.Local {

    private val listBannerLink: List<String> = listOf(
        "https://baocantho.com.vn/image/fckeditor/upload/2022/20220513/images/doctor.jpg",
        "https://prod-ripcut-delivery.disney-plus.net/v1/variant/disney" +
                "/622B600020C84ED215AA7089FCC5043DE03F7F39E41579A1EA4EEB11C5C18CBE" +
                "/scale?width=1200&aspectRatio=1.78&format=jpeg",
        "https://media.socastsrm.com/wordpress/wp-content/blogs.dir/460/files/2021/09/banner-shang-chi1.jpg",
    )

    override fun getBannerUrlList(listener: OnResultListener<List<String>>) {
        listener.onSuccess(listBannerLink)
    }

    override fun addCharacterItemToFavoriteList(item: Character): Boolean {
        return characterLocal.addCharacterFavoriteToListLocal(item)
    }

    override fun removeCharacterItemFromListLocal(id: Int) : Boolean {
        return characterLocal.removeCharacterFavoriteToListLocal(id)
    }

    override fun getFavoriteCharacterListLocal(listener: OnResultListener<MutableList<Character>>) {
        characterLocal.getCharacterListLocal(listener)
    }

    companion object {
        private var instance: HomePageLocalDataSource? = null

        fun getInstance(
            characterLocal: ICharacterDataSource.Local,
        ) = synchronized(this) {
            instance ?: HomePageLocalDataSource(characterLocal).also { instance = it }
        }
    }
}
