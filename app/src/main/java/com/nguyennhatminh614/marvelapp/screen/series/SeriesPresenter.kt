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
        // Not support
    }

    override fun onStop() {
        // Not support
    }

    override fun setView(view: SeriesContract.View?) {
        this.view = view
    }

    override fun getSeriesListFromLocal() {
        view?.showLoadingDialog()
        seriesRepository.getAllFavoriteListLocal(
            object : OnResultListener<MutableList<Series>> {
                override fun onSuccess(data: MutableList<Series>?) {
                    view?.onSuccessGetFavoriteItem(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun checkFavoriteItemExist(series: Series): Boolean {
        return seriesRepository.checkExistSeries(series)
    }

    override fun addSeriesFavoriteToListLocal(series: Series): Boolean {
        return seriesRepository.addSeriesToFavoriteList(series)
    }

    override fun removeSeriesFavoriteToListLocal(id: Int): Boolean {
        return seriesRepository.removeSeriesFromFavoriteList(id)
    }

    override fun getSeriesListRemote() {
        view?.showLoadingDialog()
        seriesRepository.getRemoteListSeries(
            object : OnResultListener<MutableList<Series>> {
                override fun onSuccess(data: MutableList<Series>?) {
                    view?.onSuccessGetDataFromRemote(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
                }
            }
        )
    }

    override fun getSeriesListRemoteWithOffset(offset: Int) {
        view?.showLoadingDialog()
        seriesRepository.getRemoteListSeriesWithOffset(offset,
            object : OnResultListener<MutableList<Series>> {
                override fun onSuccess(data: MutableList<Series>?) {
                    view?.onSuccessGetOffsetDataFromRemote(data)
                    view?.hideLoadingDialog()
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                    view?.hideLoadingDialog()
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
