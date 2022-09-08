package com.nguyennhatminh614.marvelapp.screen.series

import com.nguyennhatminh614.marvelapp.data.model.Series

interface SeriesContract {
    interface View {
        fun onSuccessGetFavoriteItem(listSeries: MutableList<Series>?)
        fun onSuccessGetDataFromRemote(listSeries: MutableList<Series>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getSeriesListFromLocal()
        fun checkFavoriteItemExist(series: Series) : Boolean?
        fun addSeriesFavoriteToListLocal(series: Series)
        fun removeSeriesFavoriteToListLocal(series: Series)
        fun getSeriesListRemote()
    }
}
