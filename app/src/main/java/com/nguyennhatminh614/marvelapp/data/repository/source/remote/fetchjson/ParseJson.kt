package com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson

import android.util.Log
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Comic
import org.json.JSONObject

class ParseJson {
    fun characterParseJson(jsonObject: JSONObject): Character {
        val character = Character()
        character.id = jsonObject.getInt(CharacterEntry.ID)
        character.name = jsonObject.getString(CharacterEntry.NAME)
        character.description = jsonObject.getString(CharacterEntry.DESCRIPTION)
        character.thumbnailLink = jsonObject.getJSONObject(CharacterEntry.THUMBNAIL_DIR)
            .getString(CharacterEntry.GET_PATH)

        character.comicList = GetDTOList.getComicList(
            jsonObject.getJSONObject(CharacterEntry.COMIC).getJSONArray(CharacterEntry.ITEM_KEYS)
        )
        character.eventList = GetDTOList.getEventList(
            jsonObject.getJSONObject(CharacterEntry.EVENT).getJSONArray(CharacterEntry.ITEM_KEYS)
        )
        character.seriesList = GetDTOList.getSeriesList(
            jsonObject.getJSONObject(CharacterEntry.SERIES).getJSONArray(CharacterEntry.ITEM_KEYS)
        )
        character.storiesList = GetDTOList.getStoriesList(
            jsonObject.getJSONObject(CharacterEntry.STORIES).getJSONArray(CharacterEntry.ITEM_KEYS)
        )

        jsonObject.getJSONArray(CharacterEntry.GET_MANY_URL).apply {
            for (i in 0 until length()) {
                val jsonObject = getJSONObject(i)
                Log.d("testJsonObject", jsonObject.toString())
                val urlString = jsonObject.getString(CharacterEntry.GET_DETAIL_URL)
                val type = jsonObject.getString(CharacterEntry.GET_TYPE)

                when (type) {
                    CharacterEntry.TYPE_DETAIL_URL -> character.detailUrl = urlString
                    CharacterEntry.TYPE_WIKI_URL -> character.wikiUrl = urlString
                    CharacterEntry.TYPE_COMIC_LINK_URL -> character.comicLinkUrl = urlString
                }
            }
        }

        return character
    }

    fun comicParseJson(jsonObject: JSONObject) = Comic().apply {
        jsonObject.let {
            id = it.getInt(CharacterEntry.ID)
            title = it.getString(CharacterEntry.TITLE)
            description = it.getString(CharacterEntry.DESCRIPTION)
            it.getJSONObject(CharacterEntry.PRICES).also { thisElem ->
                if (thisElem.getString(CharacterEntry.GET_TYPE).equals(CharacterEntry.PRINT_PRICES)) {
                    printPrice = thisElem.getDouble(CharacterEntry.DETAIL_PRICE)
                }
            }
            numberOfPages = it.getInt(CharacterEntry.NUMBER_OF_PAGE)

            thumbnailLink = it.getJSONObject(CharacterEntry.THUMBNAIL_DIR)
                .getString(CharacterEntry.GET_PATH)

            creatorList = GetDTOList.getCreatorList(
                it.getJSONObject(CharacterEntry.CREATORS).getJSONArray(CharacterEntry.ITEM_KEYS)
            )
            characterList = GetDTOList.getCharacterList(
                it.getJSONObject(CharacterEntry.CHARACTERS).getJSONArray(CharacterEntry.ITEM_KEYS)
            )
            storiesList = GetDTOList.getStoriesList(
                it.getJSONObject(CharacterEntry.STORIES).getJSONArray(CharacterEntry.ITEM_KEYS)
            )
            seriesList = GetDTOList.getSeriesList(
                it.getJSONObject(CharacterEntry.SERIES).getJSONArray(CharacterEntry.ITEM_KEYS)
            )
            eventList = GetDTOList.getEventList(
                it.getJSONObject(CharacterEntry.EVENT).getJSONArray(CharacterEntry.ITEM_KEYS)
            )

            it.getJSONObject(CharacterEntry.GET_MANY_URL).also { thisElem ->
                run {
                    if (thisElem.getString(CharacterEntry.GET_TYPE).equals(CharacterEntry.TYPE_DETAIL_URL)) {
                        comicDetailLink = thisElem.getString(CharacterEntry.GET_DETAIL_URL)
                    }
                }
            }
        }
    }
}
