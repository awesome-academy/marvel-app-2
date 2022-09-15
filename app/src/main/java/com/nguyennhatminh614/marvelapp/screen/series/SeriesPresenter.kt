package com.nguyennhatminh614.marvelapp.screen.series

import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.SeriesRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class SeriesPresenter(
    private val seriesRepository: SeriesRepository,
) : BasePresenter<SeriesContract.View>, SeriesContract.Presenter {
    private var view: SeriesContract.View? = null

    override fun onStart() {
        getSeriesListFromLocal()
        getSeriesListRemote()
    }

    override fun onStop() {
        // Not support
    }

    override fun setView(view: SeriesContract.View?) {
        this.view = view
    }

    override fun getSeriesListFromLocal() {
        seriesRepository.getAllFavoriteListLocal(
            object : OnResultListener<MutableList<Series>> {
                override fun onSuccess(data: MutableList<Series>?) {
                    view?.onSuccessGetFavoriteItem(data)
                }

                override fun onError(exception: Exception?) {
                    // Not support
                }
            }
        )
    }

    override fun checkFavoriteItemExist(series: Series): Boolean? {
        return seriesRepository.checkExistSeries(series)
    }

    override fun addSeriesFavoriteToListLocal(series: Series) {
        seriesRepository.addSeriesToFavoriteList(series)
    }

    override fun removeSeriesFavoriteToListLocal(series: Series) {
        seriesRepository.removeSeriesFromFavoriteList(series)
    }

    override fun getSeriesListRemote() {
        seriesRepository.getRemoteListSeries(
            object : OnResultListener<MutableList<Series>> {
                override fun onSuccess(data: MutableList<Series>?) {
                    view?.onSuccessGetDataFromRemote(data)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            }
        )
    }

    companion object {
        private var instance: SeriesPresenter? = null

        fun getInstance(seriesRepository: SeriesRepository) =
            synchronized(this) {
                instance ?: SeriesPresenter(seriesRepository).also { instance = it }
            }
    }
}
