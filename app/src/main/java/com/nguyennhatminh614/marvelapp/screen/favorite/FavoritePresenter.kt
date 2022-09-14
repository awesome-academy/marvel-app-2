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
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class FavoritePresenter(
    private val favoriteRepository: FavoriteRepository,
) : BasePresenter<FavoriteContract.View>, FavoriteContract.Presenter {

    private var view: FavoriteContract.View? = null

    override fun getListCharacterFavorite() {
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
                    view?.onSuccessGetCharacterFavoriteList(favoriteCharacterList)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun getListComicFavorite() {
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
                    view?.onSuccessGetComicFavoriteList(favoriteComicList)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun getListSeriesFavorite() {
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
                    view?.onSuccessGetSeriesFavoriteList(favoriteSeriesList)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun getListStoriesFavorite() {
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
                    view?.onSuccessGetStoriesFavoriteList(favoriteStoriesList)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun removeItemFromFavoriteList(favoriteItem: FavoriteItem) {
        favoriteRepository.remoteItemFromLocal(favoriteItem)
    }

    override fun getCharacterInfoByID(id: Int) {
        favoriteRepository.getCharacterListRemoteWithID(
            id,
            object : OnResultListener<Character> {
                override fun onSuccess(data: Character?) {
                    if (data != null) {
                        view?.onSuccessGetDetailCharacterData(data)
                    }
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun getComicInfoByID(id: Int) {
        favoriteRepository.getComicListRemoteWithID(
            id,
            object : OnResultListener<Comic> {
                override fun onSuccess(data: Comic?) {
                    if (data != null) {
                        view?.onSuccessGetDetailComicData(data)
                    }
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun getSeriesInfoByID(id: Int) {
        favoriteRepository.getSeriesListRemoteWithID(
            id,
            object : OnResultListener<Series> {
                override fun onSuccess(data: Series?) {
                    if (data != null) {
                        view?.onSuccessGetDetailSeriesData(data)
                    }
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun getStoriesInfoByID(id: Int) {
        favoriteRepository.getStoriesListRemoteWithID(
            id,
            object : OnResultListener<Stories> {
                override fun onSuccess(data: Stories?) {
                    if (data != null) {
                        view?.onSuccessGetDetailStoriesData(data)
                    }
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun onStart() {
        getListCharacterFavorite()
        getListSeriesFavorite()
        getListComicFavorite()
        getListStoriesFavorite()
    }

    override fun onStop() {
        // Not support
    }

    override fun setView(view: FavoriteContract.View?) {
        this.view = view
    }

    companion object {
        private var instance: FavoritePresenter? = null

        fun getInstance(favoriteRepository: FavoriteRepository) = synchronized(this) {
            instance ?: FavoritePresenter(favoriteRepository).also { instance = it }
        }
    }
}