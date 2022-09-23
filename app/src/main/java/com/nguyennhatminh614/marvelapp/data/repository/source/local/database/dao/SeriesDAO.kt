package com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao

import com.nguyennhatminh614.marvelapp.data.model.Series

interface SeriesDAO {
    fun checkExistSeries(series: Series) : Boolean
    fun getAllFavoriteSeries() : MutableList<Series>
    fun addSeriesToFavoriteList(series: Series) : Boolean
    fun removeSeriesToFavoriteList(id: Int) : Boolean
}
