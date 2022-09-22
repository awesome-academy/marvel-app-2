package com.nguyennhatminh614.marvelapp.data.repository.source.local.series

import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.ISeriesDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.SeriesDAO
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class SeriesLocalDataSource(
    private val seriesDAO: SeriesDAO
) : ISeriesDataSource.Local {

    override fun getAllFavoriteListLocal(
        listener: OnResultListener<MutableList<Series>>
    ) {
        val list = mutableListOf<Series>()
        seriesDAO.let { list.addAll(it.getAllFavoriteSeries()) }

        if (list.isNotEmpty()) {
            listener.onSuccess(list)
        } else {
            listener.onError(Exception(Constant.EMPTY_DATA_EXCEPTION))
        }
    }

    override fun checkExistSeries(series: Series): Boolean {
        return seriesDAO.checkExistSeries(series)
    }

    override fun addSeriesToFavoriteList(series: Series): Boolean {
        return seriesDAO.addSeriesToFavoriteList(series)
    }

    override fun removeSeriesFromFavoriteList(id: Int): Boolean {
        return seriesDAO.removeSeriesToFavoriteList(id)
    }

    companion object {
        private var instance: SeriesLocalDataSource? = null

        fun getInstance(seriesDAO: SeriesDAO) =
            synchronized(this) {
                instance ?: SeriesLocalDataSource(seriesDAO).also { instance = it }
            }
    }
}
