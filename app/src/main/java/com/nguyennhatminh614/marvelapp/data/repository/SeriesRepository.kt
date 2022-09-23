package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class SeriesRepository(
    private val local: ISeriesDataSource.Local,
    private val remote: ISeriesDataSource.Remote,
) : ISeriesDataSource.Local, ISeriesDataSource.Remote {

    override fun getAllFavoriteListLocal(listener: OnResultListener<MutableList<Series>>) {
        local.getAllFavoriteListLocal(listener)
    }

    override fun checkExistSeries(series: Series): Boolean {
        return local.checkExistSeries(series)
    }

    override fun addSeriesToFavoriteList(series: Series): Boolean {
        return local.addSeriesToFavoriteList(series)
    }

    override fun removeSeriesFromFavoriteList(id: Int): Boolean {
        return local.removeSeriesFromFavoriteList(id)
    }

    override fun getRemoteListSeries(listener: OnResultListener<MutableList<Series>>) {
        remote.getRemoteListSeries(listener)
    }

    override fun getRemoteListSeriesWithOffset(
        offset: Int,
        listener: OnResultListener<MutableList<Series>>,
    ) {
        remote.getRemoteListSeriesWithOffset(offset, listener)
    }

    companion object {
        private var instance: SeriesRepository? = null

        fun getInstance(local: ISeriesDataSource.Local, remote: ISeriesDataSource.Remote) =
            synchronized(this) {
                instance ?: SeriesRepository(local, remote).also { instance = it }
            }
    }
}
