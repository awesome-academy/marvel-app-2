package com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.ComicEntry
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.CreatorEntry
import com.nguyennhatminh614.marvelapp.data.model.DetailListItem
import com.nguyennhatminh614.marvelapp.data.model.DetailType
import com.nguyennhatminh614.marvelapp.data.model.DtoItem
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.EventEntry
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.SeriesEntry
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.model.StoriesEntry
import com.nguyennhatminh614.marvelapp.util.constant.APIConstant
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.convertToHTTPS
import com.nguyennhatminh614.marvelapp.util.extensions.parseDateTime
import org.json.JSONObject

const val NO_DESCRIPTION = "No description!"
const val DEFAULT_INT_VALUE = 0
const val DEFAULT_EMPTY_STRING = ""
const val DEFAULT_DOUBLE_VALUE = 0.0
const val MESSAGE_EMPTY_CHARACTER_LIST = "Doesn't have any characters"
const val MESSAGE_EMPTY_CREATOR_LIST = "Doesn't have any creators"
const val MESSAGE_EMPTY_COMIC_LIST = "Doesn't have any comics"
const val MESSAGE_EMPTY_SERIES_LIST = "Doesn't have any series"
const val MESSAGE_EMPTY_EVENT_LIST = "Doesn't have any events"
const val MESSAGE_EMPTY_STORIES_LIST = "Doesn't have any stories"

class ParseJson {

    fun characterParseJson(jsonObject: JSONObject): Character {
        var description = ""
        var thumbnailLink = ""
        val listDetailContent: MutableList<DetailListItem> = mutableListOf()
        var detailUrl = ""
        var wikiUrl = ""
        var comicLinkUrl = ""

        val id = jsonObject.optInt(CharacterEntry.ID, DEFAULT_INT_VALUE)
        val name = jsonObject.optString(CharacterEntry.NAME, DEFAULT_EMPTY_STRING)
        description =
            if (jsonObject.isNull(CharacterEntry.DESCRIPTION)) NO_DESCRIPTION
            else jsonObject.optString(CharacterEntry.DESCRIPTION)
        if (description.isBlank()) {
            description = NO_DESCRIPTION
        }
        checkNullOrNotExistsJsonObject(jsonObject, CharacterEntry.THUMBNAIL_DIR) {
            thumbnailLink = getThumbnailLink(
                it.optString(CharacterEntry.GET_PATH, DEFAULT_EMPTY_STRING),
                it.optString(CharacterEntry.EXTENSION, DEFAULT_EMPTY_STRING)
            )
        }
        val listCategory = listOf(CharacterEntry.COMIC, CharacterEntry.EVENT, CharacterEntry.SERIES,
            CharacterEntry.STORIES)
        listCategory.forEach { category ->
            val title = when (category) {
                CharacterEntry.COMIC -> ComicEntry.COMIC_ENTITY
                CharacterEntry.EVENT -> EventEntry.EVENT_ENTITY
                CharacterEntry.STORIES -> StoriesEntry.STORIES_ENTITY
                CharacterEntry.SERIES -> SeriesEntry.SERIES_ENTITY
                else -> ""
            }
            checkNullOrNotExistsJsonObject(jsonObject, category) {
                listDetailContent.addDetailList(
                    title,
                    getDetailObjectList(it.getJSONArray(CharacterEntry.ITEM_KEYS))
                )
            }
        }
        jsonObject.getJSONArray(CharacterEntry.GET_MANY_URL).apply {
            for (i in 0 until length()) {
                checkNullOrNotExistsJsonObject(getJSONObject(i)) {
                    val urlString =
                        it.optString(CharacterEntry.GET_DETAIL_URL, DEFAULT_EMPTY_STRING)
                            .convertToHTTPS()
                    when (it.optString(CharacterEntry.GET_TYPE, DEFAULT_EMPTY_STRING)) {
                        CharacterEntry.TYPE_DETAIL_URL -> detailUrl = urlString
                        CharacterEntry.TYPE_WIKI_URL -> wikiUrl = urlString
                        CharacterEntry.TYPE_COMIC_LINK_URL -> comicLinkUrl = urlString
                    }
                }
            }
        }

        return Character(id, name, description, thumbnailLink, listDetailContent, detailUrl,
            wikiUrl, comicLinkUrl)
    }

    fun comicParseJson(jsonObject: JSONObject): Comic {
        val id = jsonObject.optInt(ComicEntry.ID, DEFAULT_INT_VALUE)
        val title = jsonObject.optString(ComicEntry.TITLE, DEFAULT_EMPTY_STRING)
        var description = if (jsonObject.isNull(ComicEntry.DESCRIPTION)) NO_DESCRIPTION
        else jsonObject.optString(ComicEntry.DESCRIPTION, DEFAULT_EMPTY_STRING)
        if (description.isBlank()) description = NO_DESCRIPTION
        var printPrice = 0.0
        jsonObject.getJSONArray(ComicEntry.PRICES).apply {
            checkNullOrNotExistsJsonObject(getJSONObject(Constant.FIRST_POSITION)) {
                if (it.optString(ComicEntry.GET_TYPE, DEFAULT_EMPTY_STRING)
                        .equals(ComicEntry.PRINT_PRICES)) {
                    printPrice = it.optDouble(ComicEntry.DETAIL_PRICE, DEFAULT_DOUBLE_VALUE) }
            }
        }
        val numberOfPages = jsonObject.optInt(ComicEntry.NUMBER_OF_PAGE, DEFAULT_INT_VALUE)
        var thumbnailLink = ""
        checkNullOrNotExistsJsonObject(jsonObject, ComicEntry.THUMBNAIL_DIR) {
            thumbnailLink = getThumbnailLink(
                it.optString(ComicEntry.GET_PATH, DEFAULT_EMPTY_STRING),
                it.optString(ComicEntry.EXTENSION, DEFAULT_EMPTY_STRING)
            )
        }
        val listDetailContent = mutableListOf<DetailListItem>()
        val listCategory = listOf(ComicEntry.CREATORS, ComicEntry.EVENT, ComicEntry.CHARACTERS,
            ComicEntry.STORIES)
        listCategory.forEach { category ->
            val title = when (category) {
                ComicEntry.CHARACTERS -> CharacterEntry.CHARACTER_ENTITY
                ComicEntry.EVENT -> EventEntry.EVENT_ENTITY
                ComicEntry.STORIES -> StoriesEntry.STORIES_ENTITY
                ComicEntry.CREATORS -> CreatorEntry.CREATOR_ENTITY
                else -> ""
            }
            checkNullOrNotExistsJsonObject(jsonObject, category) {
                listDetailContent.addDetailList(title,
                    getDetailObjectList(it.getJSONArray(ComicEntry.ITEM_KEYS))
                )
            }
        }
        var seriesDetail = DtoItem()
        checkNullOrNotExistsJsonObject(jsonObject, ComicEntry.SERIES) {
            seriesDetail = DtoItem(
                resourceUrl = it.optString(APIConstant.GET_RESOURCE_URI, DEFAULT_EMPTY_STRING),
                textDescription = it.optString(APIConstant.NAME, DEFAULT_EMPTY_STRING)
            )
        }
        var comicDetailLink = ""
        jsonObject.getJSONArray(ComicEntry.GET_MANY_URL).apply {
            checkNullOrNotExistsJsonObject(getJSONObject(Constant.FIRST_POSITION)) {
                if (it.optString(ComicEntry.GET_TYPE, DEFAULT_EMPTY_STRING)
                        .equals(ComicEntry.TYPE_DETAIL_URL)) {
                    comicDetailLink = it.optString(ComicEntry.GET_DETAIL_URL, DEFAULT_EMPTY_STRING)
                        .convertToHTTPS()
                }
            }
        }
        return Comic(id, title, description, thumbnailLink, printPrice, numberOfPages, seriesDetail,
            listDetailContent, comicDetailLink)
    }

    fun creatorParseJson(jsonObject: JSONObject): Creator {
        val id = jsonObject.optInt(CreatorEntry.ID, 0)
        val name = jsonObject.optString(CreatorEntry.FULL_NAME, DEFAULT_EMPTY_STRING)
        var thumbnailLink = ""
        checkNullOrNotExistsJsonObject(jsonObject, CreatorEntry.THUMBNAIL_DIR) {
            thumbnailLink = getThumbnailLink(
                it.optString(CreatorEntry.GET_PATH, DEFAULT_EMPTY_STRING),
                it.optString(CreatorEntry.EXTENSION, DEFAULT_EMPTY_STRING)
            )
        }
        val listDetailContent = mutableListOf<DetailListItem>()
        val listCategory = listOf(CreatorEntry.COMIC, CreatorEntry.EVENT, CreatorEntry.SERIES,
            CreatorEntry.STORIES)
        listCategory.forEach { category ->
            val title = when (category) {
                CreatorEntry.COMIC -> ComicEntry.COMIC_ENTITY
                CreatorEntry.EVENT -> EventEntry.EVENT_ENTITY
                CreatorEntry.SERIES -> SeriesEntry.SERIES_ENTITY
                CreatorEntry.STORIES -> StoriesEntry.STORIES_ENTITY
                else -> ""
            }
            checkNullOrNotExistsJsonObject(jsonObject, category) {
                listDetailContent.addDetailList(
                    title,
                    getDetailObjectList(it.getJSONArray(CreatorEntry.ITEM_KEYS))
                )
            }
        }
        var detailLink = ""
        jsonObject.getJSONArray(CreatorEntry.GET_MANY_URL).apply {
            checkNullOrNotExistsJsonObject(getJSONObject(Constant.FIRST_POSITION)) {
                detailLink = it.optString(CreatorEntry.GET_DETAIL_URL, DEFAULT_EMPTY_STRING)
                    .convertToHTTPS()
            }
        }
        return Creator(id, name, thumbnailLink, listDetailContent, detailLink)
    }

    fun eventParseJson(jsonObject: JSONObject): Event {
        val id = jsonObject.optInt(EventEntry.ID, DEFAULT_INT_VALUE)
        val title = jsonObject.optString(EventEntry.TITLE, DEFAULT_EMPTY_STRING)
        var description = ""
        description = if (jsonObject.isNull(EventEntry.DESCRIPTION)) NO_DESCRIPTION
        else jsonObject.optString(EventEntry.DESCRIPTION, DEFAULT_EMPTY_STRING)
        if (description.isBlank()) {
            description = NO_DESCRIPTION
        }
        var detailLink = ""
        var wikiLink = ""
        jsonObject.getJSONArray(EventEntry.GET_MANY_URL).apply {
            for (i in 0 until length()) {
                checkNullOrNotExistsJsonObject(getJSONObject(i)) {
                    val urlString =
                        it.optString(CharacterEntry.GET_DETAIL_URL, DEFAULT_EMPTY_STRING)
                            .convertToHTTPS()
                    when (it.optString(CharacterEntry.GET_TYPE, DEFAULT_EMPTY_STRING)) {
                        EventEntry.TYPE_DETAIL_URL -> detailLink = urlString
                        EventEntry.TYPE_WIKI_URL -> wikiLink = urlString
                    }
                }
            }
        }
        val startDate =
            jsonObject.optString(EventEntry.START_DATE, DEFAULT_EMPTY_STRING).parseDateTime()
        val endDate =
            jsonObject.optString(EventEntry.END_DATE, DEFAULT_EMPTY_STRING).parseDateTime()
        var thumbnailLink = ""
        checkNullOrNotExistsJsonObject(jsonObject, EventEntry.THUMBNAIL_DIR) {
            thumbnailLink = getThumbnailLink(
                it.optString(EventEntry.GET_PATH, DEFAULT_EMPTY_STRING),
                it.optString(EventEntry.EXTENSION, DEFAULT_EMPTY_STRING),
            )
        }
        val listDetailContent = mutableListOf<DetailListItem>()
        val listCategory = listOf(EventEntry.COMIC, EventEntry.CHARACTER, EventEntry.SERIES,
            EventEntry.STORIES, EventEntry.CREATORS)
        listCategory.forEach { category ->
            val title = when (category) {
                EventEntry.COMIC -> ComicEntry.COMIC_ENTITY
                EventEntry.CHARACTER -> CharacterEntry.CHARACTER_ENTITY
                EventEntry.SERIES -> SeriesEntry.SERIES_ENTITY
                EventEntry.STORIES -> StoriesEntry.STORIES_ENTITY
                EventEntry.CREATORS -> CreatorEntry.CREATOR_ENTITY
                else -> ""
            }
            checkNullOrNotExistsJsonObject(jsonObject, category) {
                listDetailContent.addDetailList(
                    title,
                    getDetailObjectList(it.getJSONArray(EventEntry.ITEM_KEYS))
                )
            }
        }
        return Event(id, title, description, thumbnailLink, listDetailContent, startDate, endDate,
            detailLink, wikiLink)
    }

    fun seriesParseJson(jsonObject: JSONObject): Series {
        val id = jsonObject.optInt(SeriesEntry.ID, DEFAULT_INT_VALUE)
        val title = jsonObject.optString(SeriesEntry.TITLE, DEFAULT_EMPTY_STRING)
        var description = ""
        description =
            if (jsonObject.isNull(SeriesEntry.DESCRIPTION)) NO_DESCRIPTION
            else jsonObject.optString(SeriesEntry.DESCRIPTION, DEFAULT_EMPTY_STRING)
        if (description.isBlank()) {
            description = NO_DESCRIPTION
        }
        val startYear = jsonObject.optInt(SeriesEntry.START_YEAR, DEFAULT_INT_VALUE)
        val endYear = jsonObject.optInt(SeriesEntry.END_YEAR, 0)
        var detailLink = ""
        jsonObject.getJSONArray(SeriesEntry.GET_MANY_URL).apply {
            checkNullOrNotExistsJsonObject(getJSONObject(Constant.FIRST_POSITION)) {
                val urlString = it.optString(SeriesEntry.GET_DETAIL_URL, DEFAULT_EMPTY_STRING)
                    .convertToHTTPS()
                when (it.optString(SeriesEntry.GET_TYPE, DEFAULT_EMPTY_STRING)) {
                    SeriesEntry.TYPE_DETAIL_URL -> detailLink = urlString
                }
            }
        }
        var thumbnailLink = ""
        checkNullOrNotExistsJsonObject(jsonObject, SeriesEntry.THUMBNAIL_DIR) {
            thumbnailLink = getThumbnailLink(
                it.optString(SeriesEntry.GET_PATH, DEFAULT_EMPTY_STRING),
                it.optString(SeriesEntry.EXTENSION, DEFAULT_EMPTY_STRING),
            )
        }
        val listDetailContent = mutableListOf<DetailListItem>()
        val listCategory = listOf(SeriesEntry.COMIC, SeriesEntry.CHARACTER, SeriesEntry.EVENT,
            SeriesEntry.STORIES, SeriesEntry.CREATORS)
        listCategory.forEach { category ->
            val title = when (category) {
                SeriesEntry.COMIC -> ComicEntry.COMIC_ENTITY
                SeriesEntry.CHARACTER -> CharacterEntry.CHARACTER_ENTITY
                SeriesEntry.EVENT -> EventEntry.EVENT_ENTITY
                SeriesEntry.STORIES -> StoriesEntry.STORIES_ENTITY
                SeriesEntry.CREATORS -> CreatorEntry.CREATOR_ENTITY
                else -> ""
            }
            checkNullOrNotExistsJsonObject(jsonObject, category) {
                listDetailContent.addDetailList(
                    title,
                    getDetailObjectList(it.getJSONArray(SeriesEntry.ITEM_KEYS))
                )
            }
        }
        return Series(id, title, description, startYear, endYear, thumbnailLink, detailLink,
            listDetailContent)
    }

    fun storiesParseJson(jsonObject: JSONObject): Stories {
        val id = jsonObject.optInt(StoriesEntry.ID, DEFAULT_INT_VALUE)
        val title = jsonObject.optString(StoriesEntry.TITLE, DEFAULT_EMPTY_STRING)
        var description = if (jsonObject.isNull(StoriesEntry.DESCRIPTION)) NO_DESCRIPTION
        else jsonObject.optString(StoriesEntry.DESCRIPTION, DEFAULT_EMPTY_STRING)
        if (description.isBlank()) description = NO_DESCRIPTION
        var thumbnailLink = ""
        checkNullOrNotExistsJsonObject(jsonObject, StoriesEntry.THUMBNAIL_DIR) {
            thumbnailLink = getThumbnailLink(
                it.optString(StoriesEntry.GET_PATH, DEFAULT_EMPTY_STRING),
                it.optString(StoriesEntry.EXTENSION, DEFAULT_EMPTY_STRING),
            )
        }
        val listDetailContent = mutableListOf<DetailListItem>()
        val listCategory = listOf(StoriesEntry.COMIC, StoriesEntry.CHARACTER, StoriesEntry.EVENT,
            StoriesEntry.SERIES, StoriesEntry.CREATORS)
        listCategory.forEach { category ->
            val title = when (category) {
                StoriesEntry.COMIC -> ComicEntry.COMIC_ENTITY
                StoriesEntry.CHARACTER -> CharacterEntry.CHARACTER_ENTITY
                StoriesEntry.EVENT -> EventEntry.EVENT_ENTITY
                StoriesEntry.SERIES -> SeriesEntry.SERIES_ENTITY
                StoriesEntry.CREATORS -> CreatorEntry.CREATOR_ENTITY
                else -> ""
            }
            checkNullOrNotExistsJsonObject(jsonObject, category) {
                listDetailContent.addDetailList(
                    title,
                    getDetailObjectList(it.getJSONArray(StoriesEntry.ITEM_KEYS))
                )
            }
        }

        return Stories(id, title, description, thumbnailLink, listDetailContent)
    }

    private fun getThumbnailLink(path: String, extension: String): String {
        return "$path.$extension"
    }

    private fun checkNullOrNotExistsJsonObject(
        jsonObject: JSONObject,
        keyEntity: String = "",
        callback: (JSONObject) -> Unit,
    ) {
        if (keyEntity.isEmpty()) {
            callback(jsonObject)
            return
        }

        if (jsonObject.has(keyEntity) && !jsonObject.isNull(keyEntity)) {
            callback(jsonObject.getJSONObject(keyEntity))
        }
    }

    private fun MutableList<DetailListItem>.addDetailList(
        title: String,
        listDetail: MutableList<DtoItem>,
    ) {
        val emptyMessage = when (title) {
            CharacterEntry.CHARACTER_ENTITY -> MESSAGE_EMPTY_CHARACTER_LIST
            ComicEntry.COMIC_ENTITY -> MESSAGE_EMPTY_COMIC_LIST
            CreatorEntry.CREATOR_ENTITY -> MESSAGE_EMPTY_CREATOR_LIST
            SeriesEntry.SERIES_ENTITY -> MESSAGE_EMPTY_SERIES_LIST
            StoriesEntry.STORIES_ENTITY -> MESSAGE_EMPTY_STORIES_LIST
            else -> MESSAGE_EMPTY_EVENT_LIST
        }

        this.add(DetailListItem(title, DetailType.TITLE))

        if (listDetail.isNotEmpty()){
            listDetail.forEach {
                this.add(DetailListItem(it.textDescription, DetailType.CONTENT))
            }
        } else {
            this.add(DetailListItem(emptyMessage, DetailType.CONTENT))
        }
    }
}
