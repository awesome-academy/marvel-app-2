package com.nguyennhatminh614.marvelapp.data.repository.source.local.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.nguyennhatminh614.marvelapp.data.model.Character

class MyDatabase(
    context: Context?,
    name: String? = DATABASE_NAME,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = VERSION
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.apply {
            execSQL(CREATE_CHARACTER_TABLE)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.apply {
            execSQL(UPGRADE_TABLE + CHARACTER_TABLE)
        }
        onCreate(p0)
    }

    fun checkExistsCharacter(character: Character): Boolean {
        val database = readableDatabase
        val stringQuery = "select * from $CHARACTER_TABLE where ID = ${character.id}"
        val cursor = database.rawQuery(stringQuery, null)
        return cursor.count > 0
    }

    fun getAllFavoriteCharacter(): ArrayList<Character> {
        val database = readableDatabase
        val stringQuery = "select * from $CHARACTER_TABLE"
        val cursor = database.rawQuery(stringQuery, null)

        val listCharacter = ArrayList<Character>()
        cursor?.apply {
            if(this.count > 0) {
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

    fun addFavoriteNewCharacter(character: Character) {
        val database = writableDatabase
        val values = ContentValues()

        database.apply {
            values.apply {
                put(ID, character.id)
                put(NAME, character.name)
                put(DESCRIPTION, character.description)
                put(THUMBNAIL_LINK, character.thumbnailLink)
                put(FAVORITE, if (character.isFavorite) 1 else 0)
            }

            insert(CHARACTER_TABLE, null, values)
            close()
        }
    }

    fun removeFavoriteCharacter(character: Character) {
        val database = writableDatabase
        val whereClause = "ID in (?)"

        database.apply {
            delete(CHARACTER_TABLE, whereClause, arrayOf(character.id.toString()))
            close()
        }
    }

    companion object {
        private const val CHARACTER_TABLE = "CHARACTER"
        private const val DATABASE_NAME = "MarvelAppDB"
        private const val VERSION = 1
        private const val ID = "id"
        private const val NAME = "name"
        private const val DESCRIPTION = "description"
        private const val THUMBNAIL_LINK = "thumbnailLink"
        private const val FAVORITE = "favorite"
        private const val CREATE_CHARACTER_TABLE =
            "create table $CHARACTER_TABLE ($ID integer primary key, " +
                    "$NAME text, " +
                    "$DESCRIPTION text, " +
                    "$THUMBNAIL_LINK text, " +
                    "$FAVORITE integer)"
        private const val UPGRADE_TABLE = "drop table if exists"

        private var instance: MyDatabase? = null

        fun getInstance(context: Context?) =
            if (instance != null) instance else MyDatabase(context).also { instance = it }
    }
}
