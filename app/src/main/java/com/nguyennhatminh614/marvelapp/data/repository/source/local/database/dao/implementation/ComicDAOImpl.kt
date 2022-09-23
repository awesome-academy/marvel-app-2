package com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation

import android.content.ContentValues
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.ComicDAO
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class ComicDAOImpl(localDatabase: LocalDatabase) : ComicDAO {

    private val readableDatabase = localDatabase.readableDatabase
    private val writableDatabase = localDatabase.writableDatabase

    override fun checkExistComic(comic: Comic): Boolean {
        val stringQuery = "select * from $COMIC_TABLE where ID = ${comic.id}"
        val cursor = readableDatabase.rawQuery(stringQuery, null)
        return cursor.count > 0
    }

    override fun getAllFavoriteComic(): MutableList<Comic> {
        val stringQuery = "select * from $COMIC_TABLE"
        val cursor = readableDatabase.rawQuery(stringQuery, null)

        val listComic = mutableListOf<Comic>()
        cursor?.apply {
            if (this.count > 0) {
                moveToFirst()
                while (!isAfterLast) {
                    listComic.add(
                        Comic(
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

        return listComic
    }

    override fun addComicToFavoriteList(comic: Comic) : Boolean {
        val values = ContentValues()
        var result: Long
        writableDatabase.apply {
            values.apply {
                put(ID, comic.id)
                put(TITLE, comic.title)
                put(DESCRIPTION, comic.description)
                put(THUMBNAIL_LINK, comic.thumbnailLink)
                put(FAVORITE, if (comic.isFavorite) 1 else 0)
            }

            result = insert(COMIC_TABLE, null, values)
        }

        return result != Constant.STATUS_FAIL_INSERT
    }

    override fun removeComicFromFavoriteList(id: Int) : Boolean {
        val whereClause = "ID = ?"
        var result: Int
        writableDatabase.apply {
            result = delete(COMIC_TABLE, whereClause, arrayOf(id.toString()))
        }
        return result != Constant.STATUS_FAIL_DELETE
    }

    companion object {
        private const val COMIC_TABLE = "COMIC"
        private const val ID = "id"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val THUMBNAIL_LINK = "thumbnailLink"
        private const val FAVORITE = "favorite"

        private var instance: ComicDAOImpl? = null

        fun getInstance(localDatabase: LocalDatabase) =
            synchronized(this) {
                instance ?: ComicDAOImpl(localDatabase).also { instance = it }
            }
    }
}
