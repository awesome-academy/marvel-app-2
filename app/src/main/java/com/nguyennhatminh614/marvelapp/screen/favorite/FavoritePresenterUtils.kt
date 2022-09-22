package com.nguyennhatminh614.marvelapp.screen.favorite

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.ComicEntry
import com.nguyennhatminh614.marvelapp.data.model.FavoriteItem
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.SeriesEntry
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.model.StoriesEntry
import com.nguyennhatminh614.marvelapp.data.repository.FavoriteRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

object FavoritePresenterUtils {
    fun getListCharacterFavorite(view: FavoriteContract.View?, favoriteRepository: FavoriteRepository) {
        val favoriteCharacterList = mutableListOf<Any>()
        favoriteCharacterList.add(CharacterEntry.CHARACTER_ENTITY)

        favoriteRepository.getCharacterListLocal(
            object : OnResultListener<MutableList<Character>> {
                override fun onSuccess(data: MutableList<Character>?) {
                    data?.forEach {
                        favoriteCharacterList.add(
                            FavoriteItem(
                                id = it.id,
                                thumbnailLink = it.thumbnailLink,
                                title = it.name,
                                favoriteItemType = CharacterEntry.CHARACTER_ENTITY
                            )
                        )
                    }
                    view?.onSuccessGetFavoriteList(favoriteCharacterList)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    fun getListComicFavorite(view: FavoriteContract.View?, favoriteRepository: FavoriteRepository) {
        val favoriteComicList = mutableListOf<Any>()
        favoriteComicList.add(ComicEntry.COMIC_ENTITY)

        favoriteRepository.getComicListLocal(
            object : OnResultListener<MutableList<Comic>> {
                override fun onSuccess(data: MutableList<Comic>?) {
                    data?.forEach {
                        favoriteComicList.add(
                            FavoriteItem(
                                id = it.id,
                                thumbnailLink = it.thumbnailLink,
                                title = it.title,
                                favoriteItemType = ComicEntry.COMIC_ENTITY
                            )
                        )
                    }
                    view?.onSuccessGetFavoriteList(favoriteComicList)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    fun getListSeriesFavorite(view: FavoriteContract.View?, favoriteRepository: FavoriteRepository) {
        val favoriteSeriesList = mutableListOf<Any>()
        favoriteSeriesList.add(SeriesEntry.SERIES_ENTITY)

        favoriteRepository.getSeriesListLocal(
            object : OnResultListener<MutableList<Series>> {
                override fun onSuccess(data: MutableList<Series>?) {
                    data?.forEach {
                        favoriteSeriesList.add(
                            FavoriteItem(
                                id = it.id,
                                thumbnailLink = it.thumbnailLink,
                                title = it.title,
                                favoriteItemType = SeriesEntry.SERIES_ENTITY
                            )
                        )
                    }
                    view?.onSuccessGetFavoriteList(favoriteSeriesList)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    fun getListStoriesFavorite(view: FavoriteContract.View?, favoriteRepository: FavoriteRepository) {
        val favoriteStoriesList = mutableListOf<Any>()
        favoriteStoriesList.add(StoriesEntry.STORIES_ENTITY)

        favoriteRepository.getStoriesListLocal(
            object : OnResultListener<MutableList<Stories>> {
                override fun onSuccess(data: MutableList<Stories>?) {
                    data?.forEach {
                        favoriteStoriesList.add(
                            FavoriteItem(
                                id = it.id,
                                thumbnailLink = it.thumbnailLink,
                                title = it.title,
                                favoriteItemType = StoriesEntry.STORIES_ENTITY
                            )
                        )
                    }
                    view?.onSuccessGetFavoriteList(favoriteStoriesList)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }
}
