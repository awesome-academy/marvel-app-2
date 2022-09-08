package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

interface ISeriesDataSource {
    interface Local {
        fun getAllFavoriteListLocal(listener: OnResultListener<MutableList<Series>>)
        fun checkExistSeries(series: Series) : Boolean?
        fun addSeriesToFavoriteList(series: Series)
        fun removeSeriesFromFavoriteList(series: Series)
    }

    interface Remote {
        fun getRemoteListSeries(listener: OnResultListener<MutableList<Series>>)
    }
}
