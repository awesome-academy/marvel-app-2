package com.nguyennhatminh614.marvelapp.data.repository.source.remote.series

import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.SeriesEntry
import com.nguyennhatminh614.marvelapp.data.repository.ISeriesDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class SeriesRemoteDataSource : ISeriesDataSource.Remote {

    override fun getRemoteListSeries(listener: OnResultListener<MutableList<Series>>) {
        GetJsonFromUrl(GET_ALL_SERIES, SeriesEntry.SERIES_ENTITY, listener).callAPI()
    }

    override fun getRemoteListSeriesWithOffset(
        offset: Int,
        listener: OnResultListener<MutableList<Series>>,
    ) {
        GetJsonFromUrl(GET_ALL_SERIES, SeriesEntry.SERIES_ENTITY, listener).callAPI(offset = offset)
    }

    companion object {
        const val GET_ALL_SERIES = "/v1/public/series"
        private var instance : SeriesRemoteDataSource? = null

        fun getInstance() =
            synchronized(this) {
                instance ?: SeriesRemoteDataSource().also { instance = it }
            }
    }
}
