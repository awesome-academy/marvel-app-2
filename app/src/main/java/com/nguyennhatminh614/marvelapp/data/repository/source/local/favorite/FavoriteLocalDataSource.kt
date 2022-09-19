package com.nguyennhatminh614.marvelapp.data.repository.source.local.favorite

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.ComicEntry
import com.nguyennhatminh614.marvelapp.data.model.FavoriteItem
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.SeriesEntry
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.model.StoriesEntry
import com.nguyennhatminh614.marvelapp.data.repository.ICharacterDataSource
import com.nguyennhatminh614.marvelapp.data.repository.IComicDataSource
import com.nguyennhatminh614.marvelapp.data.repository.IFavoriteDataSource
import com.nguyennhatminh614.marvelapp.data.repository.ISeriesDataSource
import com.nguyennhatminh614.marvelapp.data.repository.IStoriesDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class FavoriteLocalDataSource(
    private val characterLocal: ICharacterDataSource.Local,
    private val comicLocal: IComicDataSource.Local,
    private val seriesLocal: ISeriesDataSource.Local,
    private val storiesLocal: IStoriesDataSource.Local,
) : IFavoriteDataSource.Local {

    override fun getCharacterListLocal(listener: OnResultListener<MutableList<Character>>) {
        characterLocal.getCharacterListLocal(listener)
    }

    override fun getComicListLocal(listener: OnResultListener<MutableList<Comic>>) {
        comicLocal.getAllFavoriteListLocal(listener)
    }

    override fun getSeriesListLocal(listener: OnResultListener<MutableList<Series>>) {
        seriesLocal.getAllFavoriteListLocal(listener)
    }

    override fun getStoriesListLocal(listener: OnResultListener<MutableList<Stories>>) {
        storiesLocal.getAllFavoriteListLocal(listener)
    }

    override fun remoteItemFromLocal(favoriteItem: FavoriteItem) {
        val type = favoriteItem.favoriteItemType
        val id = favoriteItem.id

        when(type) {
            CharacterEntry.CHARACTER_ENTITY -> characterLocal.removeCharacterFavoriteToListLocal(id)
            ComicEntry.COMIC_ENTITY -> comicLocal.removeComicFromFavoriteList(id)
            SeriesEntry.SERIES_ENTITY -> seriesLocal.removeSeriesFromFavoriteList(id)
            StoriesEntry.STORIES_ENTITY -> storiesLocal.removeStoriesFromFavoriteList(id)
        }
    }

    companion object {
        private var instance: FavoriteLocalDataSource? = null

        fun getInstance(
            characterLocal: ICharacterDataSource.Local,
            comicLocal: IComicDataSource.Local,
            seriesLocal: ISeriesDataSource.Local,
            storiesLocal: IStoriesDataSource.Local,
        ) = synchronized(this) {
            instance ?: FavoriteLocalDataSource(characterLocal,
                comicLocal,
                seriesLocal,
                storiesLocal).also { instance = it }
        }
    }
}
