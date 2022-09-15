package com.nguyennhatminh614.marvelapp.screen.series

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
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
import com.nguyennhatminh614.marvelapp.util.constant.Constant

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
        seriesPresenter.onStart()
    }

    override fun initEvent() {
        adapter.apply {
            registerClickItemListener(
                object : OnClickItemInterface<Series> {
                    override fun onClickItem(item: Series) {
                        findNavController().navigate(R.id.action_nav_series_to_nav_detail_series,
                            bundleOf(Constant.DETAIL_ITEM to item))
                    }
                }
            )

            registerClickFavoriteItemListener(
                object : OnClickFavoriteItemInterface<Series> {
                    override fun onFavoriteItem(item: Series) {
                        seriesPresenter.addSeriesFavoriteToListLocal(item)
                    }

                    override fun onUnfavoriteItem(item: Series) {
                        seriesPresenter.removeSeriesFavoriteToListLocal(item)
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
}
