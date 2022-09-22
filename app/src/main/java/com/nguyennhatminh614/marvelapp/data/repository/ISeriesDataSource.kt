package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

interface ISeriesDataSource {
    interface Local {
        fun getAllFavoriteListLocal(listener: OnResultListener<MutableList<Series>>)
        fun checkExistSeries(series: Series) : Boolean
        fun addSeriesToFavoriteList(series: Series) : Boolean
        fun removeSeriesFromFavoriteList(id: Int) : Boolean
    }

    interface Remote {
        fun getRemoteListSeries(listener: OnResultListener<MutableList<Series>>)
        fun getRemoteListSeriesWithOffset(offset: Int, listener: OnResultListener<MutableList<Series>>)
    }
}
