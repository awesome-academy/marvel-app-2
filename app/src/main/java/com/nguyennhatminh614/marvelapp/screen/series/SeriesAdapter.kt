package com.nguyennhatminh614.marvelapp.screen.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.databinding.SeriesItemLayoutBinding
import com.nguyennhatminh614.marvelapp.util.OnClickFavoriteItemInterface
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.extensions.loadGlideImageFromUrl

class SeriesAdapter : RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {
    private var listSeries = mutableListOf<Series>()
    private var clickItemInterface: OnClickItemInterface<Series>? = null
    private var clickFavoriteItemInterface: OnClickFavoriteItemInterface<Series>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SeriesItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listSeries[position])
    }

    override fun getItemCount(): Int = listSeries.size

    fun registerClickItemListener(clickItemInterface: OnClickItemInterface<Series>) {
        this.clickItemInterface = clickItemInterface
    }

    fun registerClickFavoriteItemListener(clickFavoriteItemInterface: OnClickFavoriteItemInterface<Series>) {
        this.clickFavoriteItemInterface = clickFavoriteItemInterface
    }

    fun updateItemData(listSeries: MutableList<Series>?) {
        listSeries?.let {
            this.listSeries.clear()
            this.listSeries.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun updateFavoriteItem(listFavoriteSeries: MutableList<Series>) {
        for (series in listFavoriteSeries) {
            listSeries.filter { return@filter it.id == series.id }.also {
                if (it.isNotEmpty()) {
                    it[0].isFavorite = true
                }
            }
        }
    }

    inner class ViewHolder(val binding: SeriesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(series: Series) {
            binding.apply {
                imageSeries.loadGlideImageFromUrl(
                    root.context,
                    series.thumbnailLink,
                    R.drawable.image_comic_default
                )
                textStartYear.text = series.startYear.toString()
                textEndYear.text = series.endYear.toString()
                textSeriesDescription.text = series.description
                textSeriesName.text = series.title

                if (series.isFavorite) {
                    buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                } else {
                    buttonFavorite.setImageResource(R.drawable.ic_favorite)
                }

                buttonFavorite.setOnClickListener {
                    if (series.isFavorite) {
                        buttonFavorite.setImageResource(R.drawable.ic_favorite_checked)
                        clickFavoriteItemInterface?.onFavoriteItem(series)
                    } else {
                        buttonFavorite.setImageResource(R.drawable.ic_favorite)
                        clickFavoriteItemInterface?.onUnfavoriteItem(series)
                    }
                    series.isFavorite = series.isFavorite.not()
                }

                layoutSeriesItem.setOnClickListener {
                    clickItemInterface?.onClickItem(series)
                }
            }
        }
    }
}
