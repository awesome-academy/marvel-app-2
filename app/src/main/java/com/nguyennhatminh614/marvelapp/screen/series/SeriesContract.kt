package com.nguyennhatminh614.marvelapp.screen.series

import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.util.ILoadingDialog

interface SeriesContract {
    interface View : ILoadingDialog {
        fun onSuccessGetFavoriteItem(listSeries: MutableList<Series>?)
        fun onSuccessGetDataFromRemote(listSeries: MutableList<Series>?)
        fun onSuccessGetOffsetDataFromRemote(listSeries: MutableList<Series>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun getSeriesListFromLocal()
        fun checkFavoriteItemExist(series: Series) : Boolean?
        fun addSeriesFavoriteToListLocal(series: Series)
        fun removeSeriesFavoriteToListLocal(id: Int)
        fun getSeriesListRemote()
        fun getSeriesListRemoteWithOffset(offset: Int)
    }
}
