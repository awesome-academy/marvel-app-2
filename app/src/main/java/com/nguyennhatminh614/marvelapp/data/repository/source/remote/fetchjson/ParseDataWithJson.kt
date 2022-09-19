package com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson

import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.ComicEntry
import com.nguyennhatminh614.marvelapp.data.model.CreatorEntry
import com.nguyennhatminh614.marvelapp.data.model.EventEntry
import com.nguyennhatminh614.marvelapp.data.model.SeriesEntry
import com.nguyennhatminh614.marvelapp.data.model.StoriesEntry
import com.nguyennhatminh614.marvelapp.util.constant.APIConstant
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import org.json.JSONObject
import java.net.HttpURLConnection

class ParseDataWithJson {

    fun parseJsonToData(jsonObject: JSONObject?, keyEntity: String): Any? {
        val data = mutableListOf<Any>()

        if (jsonObject?.getInt(APIConstant.REQUEST_CODE) != HttpURLConnection.HTTP_OK) {
            return null
        }

        val resultList = jsonObject.getJSONObject(CharacterEntry.GET_DATA)
            .getJSONArray(CharacterEntry.GET_RESPONSE_RESULT)

        for (i in 0 until resultList.length()) {
            parseJsonToObject(resultList.getJSONObject(i), keyEntity)?.let { data.add(it) }
        }

        return data
    }

    fun parseJsonToDataSingleObject(jsonObject: JSONObject?, keyEntity: String): Any? {
        if (jsonObject?.getInt(APIConstant.REQUEST_CODE) != HttpURLConnection.HTTP_OK) {
            return null
        }

        val resultList = jsonObject.getJSONObject(CharacterEntry.GET_DATA)
            .getJSONArray(CharacterEntry.GET_RESPONSE_RESULT)

        return parseJsonToObject(resultList.getJSONObject(Constant.FIRST_POSITION), keyEntity)
    }

    private fun parseJsonToObject(jsonObject: JSONObject?, keyEntity: String): Any? {
        if (jsonObject != null) {
            return when (keyEntity) {
                CharacterEntry.CHARACTER_ENTITY -> ParseJson().characterParseJson(jsonObject)
                CreatorEntry.CREATOR_ENTITY -> ParseJson().creatorParseJson(jsonObject)
                ComicEntry.COMIC_ENTITY -> ParseJson().comicParseJson(jsonObject)
                EventEntry.EVENT_ENTITY -> ParseJson().eventParseJson(jsonObject)
                SeriesEntry.SERIES_ENTITY -> ParseJson().seriesParseJson(jsonObject)
                StoriesEntry.STORIES_ENTITY -> ParseJson().storiesParseJson(jsonObject)
                else -> null
            }
        }
        return null
    }
}
