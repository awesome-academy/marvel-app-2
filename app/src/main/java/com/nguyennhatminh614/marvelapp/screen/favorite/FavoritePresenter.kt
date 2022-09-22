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

    private val characterListener = object : OnResultListener<Character> {
        override fun onSuccess(data: Character?) {
            view?.onSuccessGetDetailData(data)
        }

        override fun onError(exception: Exception?) {
            view?.onError(exception)
        }
    }

    private val comicListener = object : OnResultListener<Comic> {
        override fun onSuccess(data: Comic?) {
            view?.onSuccessGetDetailData(data)
        }

        override fun onError(exception: Exception?) {
            view?.onError(exception)
        }
    }

    private val seriesListener = object : OnResultListener<Series> {
        override fun onSuccess(data: Series?) {
            view?.onSuccessGetDetailData(data)
        }

        override fun onError(exception: Exception?) {
            view?.onError(exception)
        }
    }

    private val storiesListener = object : OnResultListener<Stories> {
        override fun onSuccess(data: Stories?) {
            view?.onSuccessGetDetailData(data)
        }

        override fun onError(exception: Exception?) {
            view?.onError(exception)
        }
    }

    override fun removeItemFromFavoriteList(favoriteItem: FavoriteItem): Boolean {
        return favoriteRepository.removeItemFromLocal(favoriteItem)
    }

    override fun getListCharacterFavorite() {
        view?.showLoadingDialog()
        favoriteRepository.getCharacterListLocal(
            object : OnResultListener<MutableList<Character>> {
                override fun onSuccess(data: MutableList<Character>?) {
                    view?.onSuccessGetFavoriteList(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun getListSeriesFavorite() {
        view?.showLoadingDialog()
        favoriteRepository.getSeriesListLocal(
            object : OnResultListener<MutableList<Series>> {
                override fun onSuccess(data: MutableList<Series>?) {
                    view?.onSuccessGetFavoriteList(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun getListStoriesFavorite() {
        view?.showLoadingDialog()
        favoriteRepository.getStoriesListLocal(
            object : OnResultListener<MutableList<Stories>> {
                override fun onSuccess(data: MutableList<Stories>?) {
                    view?.onSuccessGetFavoriteList(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun getListComicFavorite() {
        view?.showLoadingDialog()
        favoriteRepository.getComicListLocal(
            object : OnResultListener<MutableList<Comic>> {
                override fun onSuccess(data: MutableList<Comic>?) {
                    view?.onSuccessGetFavoriteList(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun getCategoryInfoByID(type: String, id: Int) {
        when (type) {
            CharacterEntry.CHARACTER_ENTITY -> favoriteRepository.getCharacterListRemoteWithID(id,
                characterListener)
            ComicEntry.COMIC_ENTITY -> favoriteRepository.getComicListRemoteWithID(id,
                comicListener)
            SeriesEntry.SERIES_ENTITY -> favoriteRepository.getSeriesListRemoteWithID(id,
                seriesListener)
            StoriesEntry.STORIES_ENTITY -> favoriteRepository.getStoriesListRemoteWithID(id,
                storiesListener)
        }
    }

    override fun onStart() {
        // Not support
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
