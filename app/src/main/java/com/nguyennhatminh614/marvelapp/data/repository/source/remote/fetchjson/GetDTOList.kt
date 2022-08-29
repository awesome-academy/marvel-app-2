package com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson

import com.nguyennhatminh614.marvelapp.data.model.CharacterDTO
import com.nguyennhatminh614.marvelapp.data.model.ComicDTO
import com.nguyennhatminh614.marvelapp.data.model.CreatorDTO
import com.nguyennhatminh614.marvelapp.data.model.EventDTO
import com.nguyennhatminh614.marvelapp.data.model.SeriesDTO
import com.nguyennhatminh614.marvelapp.data.model.StoriesDTO
import com.nguyennhatminh614.marvelapp.util.constant.APIConstant
import org.json.JSONArray

object GetDTOList {
    fun getComicList(jsonArray: JSONArray) = ArrayList<ComicDTO>().apply {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            this.add(
                ComicDTO().apply {
                    resourceUrl = jsonObject.getString(APIConstant.GET_RESOURCE_URI)
                    textDescription = jsonObject.getString(APIConstant.NAME)
                }
            )
        }
    }

    fun getEventList(jsonArray: JSONArray) = ArrayList<EventDTO>().apply {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            this.add(
                EventDTO().apply {
                    resourceUrl = jsonObject.getString(APIConstant.GET_RESOURCE_URI)
                    textDescription = jsonObject.getString(APIConstant.NAME)
                }
            )
        }
    }

    fun getSeriesList(jsonArray: JSONArray) = ArrayList<SeriesDTO>().apply {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            this.add(
                SeriesDTO().apply {
                    resourceUrl = jsonObject.getString(APIConstant.GET_RESOURCE_URI)
                    textDescription = jsonObject.getString(APIConstant.NAME)
                }
            )
        }
    }

    fun getStoriesList(jsonArray: JSONArray) = ArrayList<StoriesDTO>().apply {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            this.add(
                StoriesDTO().apply {
                    resourceUrl = jsonObject.getString(APIConstant.GET_RESOURCE_URI)
                    textDescription = jsonObject.getString(APIConstant.NAME)
                }
            )
        }
    }

    fun getCreatorList(jsonArray: JSONArray) = ArrayList<CreatorDTO>().apply {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            this.add(
                CreatorDTO().apply {
                    resourceUrl = jsonObject.getString(APIConstant.GET_RESOURCE_URI)
                    textDescription = jsonObject.getString(APIConstant.NAME)
                }
            )
        }
    }

    fun getCharacterList(jsonArray: JSONArray) = ArrayList<CharacterDTO>().apply {
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            this.add(
                CharacterDTO().apply {
                    resourceUrl = jsonObject.getString(APIConstant.GET_RESOURCE_URI)
                    textDescription = jsonObject.getString(APIConstant.NAME)
                }
            )
        }
    }
}

