package com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation

import android.content.ContentValues
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.StoriesDAO
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class StoriesDAOImpl(localDatabase: LocalDatabase) : StoriesDAO {

    private val readableDatabase = localDatabase.readableDatabase
    private val writableDatabase = localDatabase.writableDatabase

    override fun checkExistStories(stories: Stories): Boolean {
        val database = readableDatabase
        val stringQuery = "select * from $STORIES_TABLE where ID = ${stories.id}"
        val cursor = database.rawQuery(stringQuery, null)
        return cursor.count > 0
    }

    override fun getAllFavoriteStories(): MutableList<Stories> {
        val database = readableDatabase
        val stringQuery = "select * from $STORIES_TABLE"
        val cursor = database.rawQuery(stringQuery, null)

        val listStories = mutableListOf<Stories>()
        cursor?.apply {
            if (this.count > 0) {
                moveToFirst()
                while (!isAfterLast) {
                    listStories.add(
                        Stories(
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

        return listStories
    }

    override fun addStoriesToFavoriteList(stories: Stories) : Boolean {
        val database = writableDatabase
        val values = ContentValues()
        var result: Long
        database.apply {
            values.apply {
                put(ID, stories.id)
                put(TITLE, stories.title)
                put(DESCRIPTION, stories.description)
                put(THUMBNAIL_LINK, stories.thumbnailLink)
                put(FAVORITE, if (stories.isFavorite) 1 else 0)
            }

            result = insert(STORIES_TABLE, null, values)
        }

        return result != Constant.STATUS_FAIL_INSERT
    }

    override fun removeStoriesToFavoriteList(id: Int) : Boolean {
        val database = writableDatabase
        val whereClause = "ID = ?"
        var result: Int
        database.apply {
            result = delete(STORIES_TABLE, whereClause, arrayOf(id.toString()))
        }

        return result != Constant.STATUS_FAIL_DELETE
    }

    companion object {
        private const val STORIES_TABLE = "STORIES"
        private const val ID = "id"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val THUMBNAIL_LINK = "thumbnailLink"
        private const val FAVORITE = "favorite"

        private var instance: StoriesDAOImpl? = null

        fun getInstance(localDatabase: LocalDatabase) =
            synchronized(this) {
                instance ?: StoriesDAOImpl(localDatabase).also { instance = it }
            }
    }
}
