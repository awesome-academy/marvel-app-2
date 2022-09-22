package com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation

import android.content.ContentValues
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.SeriesDAO
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class SeriesDAOImpl(localDatabase: LocalDatabase) : SeriesDAO {

    private val readableDatabase = localDatabase.readableDatabase
    private val writableDatabase = localDatabase.writableDatabase

    override fun checkExistSeries(series: Series): Boolean {
        val stringQuery = "select * from $SERIES_TABLE where ID = ${series.id}"
        val cursor = readableDatabase.rawQuery(stringQuery, null)
        return cursor.count > 0
    }

    override fun getAllFavoriteSeries(): MutableList<Series> {
        val stringQuery = "select * from $SERIES_TABLE"
        val cursor = readableDatabase.rawQuery(stringQuery, null)

        val listSeries = mutableListOf<Series>()
        cursor?.apply {
            if (this.count > 0) {
                moveToFirst()
                while (!isAfterLast) {
                    listSeries.add(
                        Series(
                            id = getInt(getColumnIndexOrThrow(ID)),
                            title = getString(getColumnIndexOrThrow(TITLE)),
                            description = getString(getColumnIndexOrThrow(DESCRIPTION)),
                            thumbnailLink = getString(getColumnIndexOrThrow(THUMBNAIL_LINK)),
                            isFavorite = (getInt(getColumnIndexOrThrow(FAVORITE)) == 1)
                        )
                    )
                    moveToNext()
                }
            }
        }

        return listSeries
    }

    override fun addSeriesToFavoriteList(series: Series) : Boolean {
        val values = ContentValues()
        var result: Long
        writableDatabase.apply {
            values.apply {
                put(ID, series.id)
                put(TITLE, series.title)
                put(DESCRIPTION, series.description)
                put(THUMBNAIL_LINK, series.thumbnailLink)
                put(FAVORITE, if (series.isFavorite) 1 else 0)
            }

            result = insert(SERIES_TABLE, null, values)
        }

        return result != Constant.STATUS_FAIL_INSERT
    }

    override fun removeSeriesToFavoriteList(id: Int) : Boolean {
        val whereClause = "ID in (?)"
        var result: Int
        writableDatabase.apply {
            result = delete(SERIES_TABLE, whereClause, arrayOf(id.toString()))
        }
        return result != Constant.STATUS_FAIL_DELETE
    }

    companion object {
        private const val SERIES_TABLE = "SERIES"
        private const val ID = "id"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val THUMBNAIL_LINK = "thumbnailLink"
        private const val FAVORITE = "favorite"

        private var instance: SeriesDAOImpl? = null

        fun getInstance(localDatabase: LocalDatabase) =
            synchronized(this) {
                instance ?: SeriesDAOImpl(localDatabase).also { instance = it }
            }
    }
}
