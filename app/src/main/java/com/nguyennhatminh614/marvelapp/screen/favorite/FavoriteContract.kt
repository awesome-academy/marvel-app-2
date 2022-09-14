package com.nguyennhatminh614.marvelapp.screen.favorite

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.FavoriteItem
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.Stories

interface FavoriteContract {
    interface View {
        fun onSuccessGetCharacterFavoriteList(data: MutableList<Any>)
        fun onSuccessGetComicFavoriteList(data: MutableList<Any>)
        fun onSuccessGetSeriesFavoriteList(data: MutableList<Any>)
        fun onSuccessGetStoriesFavoriteList(data: MutableList<Any>)
        fun onSuccessGetDetailCharacterData(data: Character)
        fun onSuccessGetDetailComicData(data: Comic)
        fun onSuccessGetDetailSeriesData(data: Series)
        fun onSuccessGetDetailStoriesData(data: Stories)
        fun onError(exception: Exception)
    }

    interface Presenter {
        fun getListCharacterFavorite()
        fun getListComicFavorite()
        fun getListSeriesFavorite()
        fun getListStoriesFavorite()
        fun removeItemFromFavoriteList(favoriteItem: FavoriteItem)
        fun getCharacterInfoByID(id: Int)
        fun getComicInfoByID(id: Int)
        fun getSeriesInfoByID(id: Int)
        fun getStoriesInfoByID(id: Int)
    }
}