package com.nguyennhatminh614.marvelapp.data.repository.source.remote.favorite

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.ComicEntry
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.SeriesEntry
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.model.StoriesEntry
import com.nguyennhatminh614.marvelapp.data.repository.IFavoriteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.character.CharacterRemoteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.comic.ComicRemoteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.series.SeriesRemoteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.stories.StoriesRemoteDataSource

class FavoriteRemoteDataSource : IFavoriteDataSource.Remote {
    override fun getCharacterListRemoteWithID(id: Int, listener: OnResultListener<Character>) {
        GetJsonFromUrl(
            CharacterRemoteDataSource.GET_ALL_CHARACTER + "/$id",
            CharacterEntry.CHARACTER_ENTITY,
            listener,
        ).callAPI(isSingleObject = true)
    }

    override fun getComicListRemoteWithID(id: Int, listener: OnResultListener<Comic>) {
        GetJsonFromUrl(ComicRemoteDataSource.GET_ALL_COMIC + "/$id",
            ComicEntry.COMIC_ENTITY,
            listener).callAPI(isSingleObject = true)
    }

    override fun getSeriesListRemoteWithID(id: Int, listener: OnResultListener<Series>) {
        GetJsonFromUrl(SeriesRemoteDataSource.GET_ALL_SERIES + "/$id",
            SeriesEntry.SERIES_ENTITY,
            listener).callAPI(isSingleObject = true)
    }

    override fun getStoriesListRemoteWithID(id: Int, listener: OnResultListener<Stories>) {
        GetJsonFromUrl(StoriesRemoteDataSource.GET_ALL_STORIES + "/$id",
            StoriesEntry.STORIES_ENTITY,
            listener).callAPI(isSingleObject = true)
    }

    companion object {
        private var instance: FavoriteRemoteDataSource? = null

        fun getInstance() = synchronized(this) {
            instance ?: FavoriteRemoteDataSource().also { instance = it }
        }
    }
}
