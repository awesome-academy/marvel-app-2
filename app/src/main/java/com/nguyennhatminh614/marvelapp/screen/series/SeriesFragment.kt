package com.nguyennhatminh614.marvelapp.screen.series

import android.widget.Toast
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.SeriesRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.SeriesDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.series.SeriesLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.series.SeriesRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerSeriesBinding
import com.nguyennhatminh614.marvelapp.util.OnClickFavoriteItemInterface
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment

class SeriesFragment :
    BaseFragment<FragmentDrawerSeriesBinding>(FragmentDrawerSeriesBinding::inflate),
    SeriesContract.View {
    
    private val listFavoriteSeriesLocal = mutableListOf<Series>()
    private val listRemoteSeries = mutableListOf<Series>()

    private val seriesPresenter by lazy {
        SeriesPresenter.getInstance(
            SeriesRepository.getInstance(
                SeriesLocalDataSource.getInstance(
                    SeriesDAOImpl.getInstance(
                        LocalDatabase.getInstance(context)
                    )
                ),
                SeriesRemoteDataSource.getInstance()
            )
        )
    }

    private var adapter = SeriesAdapter()

    override fun initData() {
        viewBinding.recyclerViewSeries.adapter = adapter
    }

    override fun initialize() {
        seriesPresenter.setView(this)
    }

    override fun callData() {
        seriesPresenter.getSeriesListRemote()
        seriesPresenter.getSeriesListFromLocal()
    }

    override fun initEvent() {
        adapter.apply {
            registerClickItemListener(
                object : OnClickItemInterface<Series> {
                    override fun onClickItem(item: Series) {
                        parentFragmentManager.beginTransaction()
                            .replace(
                                R.id.nav_host_fragment_content_base,
                                DetailSeriesFragment.newInstance(item)
                            ).commit()
                    }
                }
            )

            registerClickFavoriteItemListener(
                object : OnClickFavoriteItemInterface<Series> {
                    override fun onFavoriteItem(item: Series) {
                        val checkExist = seriesPresenter.checkFavoriteItemExist(item)
                        if (checkExist != null && checkExist == false) {
                            seriesPresenter.addSeriesFavoriteToListLocal(item)
                        }
                    }

                    override fun onUnfavoriteItem(item: Series) {
                        val checkExist = seriesPresenter.checkFavoriteItemExist(item)
                        if (checkExist != null && checkExist == true) {
                            seriesPresenter.removeSeriesFavoriteToListLocal(item)
                        }
                    }
                }
            )
        }
    }
    override fun onSuccessGetFavoriteItem(listSeries: MutableList<Series>?) {
        listSeries?.let { listFavoriteSeriesLocal.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateFavoriteItem(listFavoriteSeriesLocal)
        }
    }

    override fun onSuccessGetDataFromRemote(listSeries: MutableList<Series>?) {
        listSeries?.let { listRemoteSeries.addAll(it) }
        activity?.runOnUiThread {
            adapter.updateItemData(listRemoteSeries)
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = SeriesFragment()
    }
}
