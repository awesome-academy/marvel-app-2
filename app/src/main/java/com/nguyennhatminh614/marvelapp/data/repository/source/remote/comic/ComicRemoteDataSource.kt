package com.nguyennhatminh614.marvelapp.data.repository.source.remote.comic

import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.ComicEntry
import com.nguyennhatminh614.marvelapp.data.repository.IComicDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class ComicRemoteDataSource : IComicDataSource.Remote {
    override fun getRemoteListComic(listener: OnResultListener<MutableList<Comic>>) {
       GetJsonFromUrl(GET_ALL_COMIC, ComicEntry.COMIC_ENTITY, listener)
    }

    companion object {
        const val GET_ALL_COMIC = "/v1/public/comics"
        private var instance : ComicRemoteDataSource? = null

        fun getInstance() =
            synchronized(this) {
                instance ?: ComicRemoteDataSource().also { instance = it }
            }
    }
}
