package com.nguyennhatminh614.marvelapp.data.repository.source.local.database

import android.content.ContentValues
import android.content.Context
import com.nguyennhatminh614.marvelapp.data.model.Character

class CharacterTableImpl(context: Context?) : MyDatabase(context){
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
        private const val ID = "id"
        private const val NAME = "name"
        private const val DESCRIPTION = "description"
        private const val THUMBNAIL_LINK = "thumbnailLink"
        private const val FAVORITE = "favorite"

        private var instance: CharacterTableImpl? = null

        fun getInstance(context: Context?) =
            if (instance != null) instance else CharacterTableImpl(context).also { instance = it }
    }
}
