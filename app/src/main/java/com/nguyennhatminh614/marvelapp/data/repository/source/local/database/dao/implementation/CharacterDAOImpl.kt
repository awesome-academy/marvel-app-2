package com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation

import android.content.ContentValues
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.CharacterDAO
import com.nguyennhatminh614.marvelapp.util.constant.Constant


class CharacterDAOImpl(localDatabase: LocalDatabase) : CharacterDAO {

    private val readableDatabase = localDatabase.readableDatabase
    private val writableDatabase = localDatabase.writableDatabase

    override fun checkExistsCharacter(character: Character): Boolean {
        val stringQuery = "select * from $CHARACTER_TABLE where ID = ${character.id}"
        val cursor = readableDatabase.rawQuery(stringQuery, null)
        return cursor.count > 0
    }

    override fun getAllFavoriteCharacter(): ArrayList<Character> {
        val stringQuery = "select * from $CHARACTER_TABLE"
        val cursor = readableDatabase.rawQuery(stringQuery, null)

        val listCharacter = ArrayList<Character>()
        cursor?.apply {
            if (this.count > 0) {
                moveToFirst()
                while (!isAfterLast) {
                    listCharacter.add(
                        Character(
                            id = getInt(getColumnIndexOrThrow(ID)),
                            name = getString(getColumnIndexOrThrow(NAME)),
                            description = getString(getColumnIndexOrThrow(DESCRIPTION)),
                            thumbnailLink = getString(getColumnIndexOrThrow(THUMBNAIL_LINK)),
                            isFavorite = (getInt(getColumnIndexOrThrow(FAVORITE)) == 1)
                        )
                    )
                    moveToNext()
                }
            }
        }

        return listCharacter
    }

    override fun addFavoriteNewCharacter(character: Character) : Boolean {
        val values = ContentValues()
        var status: Long = 0L
        writableDatabase.apply {
            values.apply {
                put(ID, character.id)
                put(NAME, character.name)
                put(DESCRIPTION, character.description)
                put(THUMBNAIL_LINK, character.thumbnailLink)
                put(FAVORITE, if (character.isFavorite) 1 else 0)
            }

            status = insert(CHARACTER_TABLE, null, values)
        }

        return status != Constant.STATUS_FAIL_INSERT
    }

    override fun removeFavoriteCharacter(id: Int) : Boolean {
        val whereClause = "ID = ?"
        var status: Int
        writableDatabase.apply {
            status = delete(CHARACTER_TABLE, whereClause, arrayOf(id.toString()))
        }
        return status != Constant.STATUS_FAIL_DELETE
    }

    companion object {
        private const val CHARACTER_TABLE = "CHARACTER"
        private const val ID = "id"
        private const val NAME = "name"
        private const val DESCRIPTION = "description"
        private const val THUMBNAIL_LINK = "thumbnailLink"
        private const val FAVORITE = "favorite"

        private var instance: CharacterDAOImpl? = null

        fun getInstance(localDatabase: LocalDatabase) =
            synchronized(this) {
                instance ?: CharacterDAOImpl(localDatabase).also { instance = it }
            }
    }
}
