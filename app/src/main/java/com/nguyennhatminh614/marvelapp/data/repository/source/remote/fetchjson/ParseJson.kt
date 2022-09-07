package com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.ComicEntry
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.CreatorEntry
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.EventEntry
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.SeriesDTO
import com.nguyennhatminh614.marvelapp.data.model.SeriesEntry
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.model.StoriesEntry
import com.nguyennhatminh614.marvelapp.util.constant.APIConstant
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import com.nguyennhatminh614.marvelapp.util.extensions.parseDateTime
import org.json.JSONObject

const val NO_DESCRIPTION = "No description!"

class ParseJson {

    fun characterParseJson(jsonObject: JSONObject): Character {
        val character = Character()
        character.id = jsonObject.optInt(CharacterEntry.ID)
        character.name = jsonObject.optString(CharacterEntry.NAME)
        jsonObject.optString(CharacterEntry.DESCRIPTION).apply {
            character.description = ifEmpty { NO_DESCRIPTION }
        }
        checkNullOrNotExistsJsonObject(jsonObject, CharacterEntry.THUMBNAIL_DIR) {
            character.thumbnailLink = getThumbnailLink(
                it.optString(CharacterEntry.GET_PATH),
                it.optString(CharacterEntry.EXTENSION)
            )
        }
        checkNullOrNotExistsJsonObject(jsonObject, CharacterEntry.COMIC) {
            character.comicList.clear()
            character.comicList.addAll(getComicList(it.getJSONArray(CharacterEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, CharacterEntry.EVENT) {
            character.eventList.clear()
            character.eventList.addAll(getEventList(it.getJSONArray(CharacterEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, CharacterEntry.SERIES) {
            character.seriesList.clear()
            character.seriesList.addAll(getSeriesList(it.getJSONArray(CharacterEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, CharacterEntry.STORIES) {
            character.storiesList.clear()
            character.storiesList.addAll(getStoriesList(it.getJSONArray(CharacterEntry.ITEM_KEYS)))
        }
        jsonObject.getJSONArray(CharacterEntry.GET_MANY_URL).apply {
            for (i in 0 until length()) {
                checkNullOrNotExistsJsonObject(getJSONObject(i)) {
                    val urlString = it.optString(CharacterEntry.GET_DETAIL_URL)
                    when (it.optString(CharacterEntry.GET_TYPE)) {
                        CharacterEntry.TYPE_DETAIL_URL -> character.detailUrl = urlString
                        CharacterEntry.TYPE_WIKI_URL -> character.wikiUrl = urlString
                        CharacterEntry.TYPE_COMIC_LINK_URL -> character.comicLinkUrl = urlString
                    }
                }
            }
        }
        return character
    }

    fun comicParseJson(jsonObject: JSONObject): Comic {
        val comic = Comic()
        comic.id = jsonObject.optInt(ComicEntry.ID)
        comic.title = jsonObject.optString(ComicEntry.TITLE)
        comic.description = jsonObject.optString(ComicEntry.DESCRIPTION)
        if (comic.description.isEmpty()) {
            comic.description = NO_DESCRIPTION
        }
        jsonObject.getJSONArray(ComicEntry.PRICES).apply {
            checkNullOrNotExistsJsonObject(getJSONObject(Constant.FIRST_POSITION)) {
                if (it.optString(ComicEntry.GET_TYPE).equals(ComicEntry.PRINT_PRICES)) {
                    comic.printPrice = it.optDouble(ComicEntry.DETAIL_PRICE)
                }
            }
        }
        comic.numberOfPages = jsonObject.optInt(ComicEntry.NUMBER_OF_PAGE)
        checkNullOrNotExistsJsonObject(jsonObject, ComicEntry.THUMBNAIL_DIR) {
            comic.thumbnailLink = getThumbnailLink(
                it.optString(ComicEntry.GET_PATH),
                it.optString(ComicEntry.EXTENSION)
            )
        }
        checkNullOrNotExistsJsonObject(jsonObject, ComicEntry.CREATORS) {
            comic.creatorList.clear()
            comic.creatorList.addAll(getCreatorList(it.getJSONArray(ComicEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, ComicEntry.EVENT) {
            comic.eventList.clear()
            comic.eventList.addAll(getEventList(it.getJSONArray(ComicEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, ComicEntry.CHARACTERS) {
            comic.characterList.clear()
            comic.characterList.addAll(getCharacterList(it.getJSONArray(ComicEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, ComicEntry.STORIES) {
            comic.storiesList.clear()
            comic.storiesList.addAll(getStoriesList(it.getJSONArray(ComicEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject,ComicEntry.SERIES) {
            comic.seriesDetail = SeriesDTO().apply {
                resourceUrl = it.optString(APIConstant.GET_RESOURCE_URI)
                textDescription = it.optString(APIConstant.NAME)
            }
        }
        jsonObject.getJSONArray(ComicEntry.GET_MANY_URL).apply {
            checkNullOrNotExistsJsonObject(getJSONObject(Constant.FIRST_POSITION)) {
                if (it.optString(ComicEntry.GET_TYPE).equals(ComicEntry.TYPE_DETAIL_URL)) {
                    comic.comicDetailLink = it.optString(ComicEntry.GET_DETAIL_URL)
                }
            }
        }
        return comic
    }

    fun creatorParseJson(jsonObject: JSONObject): Creator {
        val creator = Creator()
        creator.id = jsonObject.optInt(CreatorEntry.ID)
        creator.name = jsonObject.optString(CreatorEntry.FULL_NAME)
        checkNullOrNotExistsJsonObject(jsonObject, CreatorEntry.THUMBNAIL_DIR) {
            creator.thumbnailLink = getThumbnailLink(
                it.optString(CreatorEntry.GET_PATH),
                it.optString(CreatorEntry.EXTENSION)
            )
        }
        checkNullOrNotExistsJsonObject(jsonObject, CreatorEntry.COMIC) {
            creator.comicList.clear()
            creator.comicList.addAll(getComicList(it.getJSONArray(CreatorEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, CreatorEntry.EVENT) {
            creator.eventList.clear()
            creator.eventList.addAll(getEventList(it.getJSONArray(CreatorEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, CreatorEntry.SERIES) {
            creator.seriesList.clear()
            creator.seriesList.addAll(getSeriesList(it.getJSONArray(CreatorEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, CreatorEntry.STORIES) {
            creator.storiesList.clear()
            creator.storiesList.addAll(getStoriesList(it.getJSONArray(CreatorEntry.ITEM_KEYS)))
        }
        jsonObject.getJSONArray(CreatorEntry.GET_MANY_URL).apply {
            checkNullOrNotExistsJsonObject(getJSONObject(Constant.FIRST_POSITION)) {
                creator.detailLink = it.optString(CreatorEntry.GET_DETAIL_URL)
            }
        }
        return creator
    }

    fun eventParseJson(jsonObject: JSONObject): Event {
        val event = Event()
        event.id = jsonObject.optInt(EventEntry.ID)
        event.title = jsonObject.optString(EventEntry.TITLE)
        jsonObject.optString(EventEntry.DESCRIPTION).apply {
            event.description = ifEmpty { NO_DESCRIPTION }
        }
        jsonObject.getJSONArray(EventEntry.GET_MANY_URL).apply {
            for (i in 0 until length()) {
                checkNullOrNotExistsJsonObject(getJSONObject(i)) {
                    val urlString = it.optString(CharacterEntry.GET_DETAIL_URL)
                    when (it.optString(CharacterEntry.GET_TYPE)) {
                        EventEntry.TYPE_DETAIL_URL -> event.detailLink = urlString
                        EventEntry.TYPE_WIKI_URL -> event.wikiLink = urlString
                    }
                }
            }
        }
        event.startDate = jsonObject.optString(EventEntry.START_DATE).parseDateTime()
        event.endDate = jsonObject.optString(EventEntry.END_DATE).parseDateTime()
        checkNullOrNotExistsJsonObject(jsonObject, EventEntry.THUMBNAIL_DIR) {
            event.thumbnailLink = getThumbnailLink(
                it.optString(EventEntry.GET_PATH),
                it.optString(EventEntry.EXTENSION),
            )
        }
        checkNullOrNotExistsJsonObject(jsonObject, EventEntry.COMIC) {
            event.comicList.clear()
            event.comicList.addAll(getComicList(it.getJSONArray(EventEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, EventEntry.CHARACTER) {
            event.characterList.clear()
            event.characterList.addAll(getCharacterList(it.getJSONArray(EventEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, EventEntry.SERIES) {
            event.seriesList.clear()
            event.seriesList.addAll(getSeriesList(it.getJSONArray(EventEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, EventEntry.STORIES) {
            event.storiesList.clear()
            event.storiesList.addAll(getStoriesList(it.getJSONArray(EventEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, EventEntry.CREATORS) {
            event.creatorList.clear()
            event.creatorList.addAll(getCreatorList(it.getJSONArray(EventEntry.ITEM_KEYS)))
        }
        return event
    }

    fun seriesParseJson(jsonObject: JSONObject): Series {
        val series = Series()
        series.id = jsonObject.optInt(SeriesEntry.ID)
        series.title = jsonObject.optString(SeriesEntry.TITLE)
        jsonObject.optString(SeriesEntry.DESCRIPTION).apply {
            series.description = ifEmpty { NO_DESCRIPTION }
        }
        series.startYear = jsonObject.optInt(SeriesEntry.START_YEAR)
        series.endYear = jsonObject.optInt(SeriesEntry.END_YEAR)
        jsonObject.getJSONArray(SeriesEntry.GET_MANY_URL).apply {
            checkNullOrNotExistsJsonObject(getJSONObject(Constant.FIRST_POSITION)) {
                val urlString = it.optString(SeriesEntry.GET_DETAIL_URL)
                when (it.optString(SeriesEntry.GET_TYPE)) {
                    SeriesEntry.TYPE_DETAIL_URL -> series.detailLink = urlString
                }
            }
        }
        series.rating = jsonObject.optString(SeriesEntry.RATING)
        checkNullOrNotExistsJsonObject(jsonObject, SeriesEntry.THUMBNAIL_DIR) {
            series.thumbnailLink = getThumbnailLink(
                it.optString(SeriesEntry.GET_PATH),
                it.optString(SeriesEntry.EXTENSION),
            )
        }
        checkNullOrNotExistsJsonObject(jsonObject, SeriesEntry.COMIC) {
            series.comicList.clear()
            series.comicList.addAll(getComicList(it.getJSONArray(SeriesEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, SeriesEntry.CHARACTER) {
            series.characterList.clear()
            series.characterList.addAll(getCharacterList(it.getJSONArray(SeriesEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, SeriesEntry.EVENT) {
            series.eventList.clear()
            series.eventList.addAll(getEventList(it.getJSONArray(SeriesEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, SeriesEntry.STORIES) {
            series.storiesList.clear()
            series.storiesList.addAll(getStoriesList(it.getJSONArray(SeriesEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, SeriesEntry.CREATORS) {
            series.creatorList.clear()
            series.creatorList.addAll(getCreatorList(it.getJSONArray(SeriesEntry.ITEM_KEYS)))
        }
        return series
    }

    fun storiesParseJson(jsonObject: JSONObject): Stories {
        val stories = Stories()
        stories.id = jsonObject.optInt(StoriesEntry.ID)
        stories.title = jsonObject.optString(StoriesEntry.TITLE)
        jsonObject.optString(StoriesEntry.DESCRIPTION).apply {
            stories.description = ifEmpty { NO_DESCRIPTION }
        }
        checkNullOrNotExistsJsonObject(jsonObject, StoriesEntry.THUMBNAIL_DIR) {
            stories.thumbnailLink = getThumbnailLink(
                it.optString(StoriesEntry.GET_PATH),
                it.optString(StoriesEntry.EXTENSION),
            )
        }
        checkNullOrNotExistsJsonObject(jsonObject, StoriesEntry.COMIC) {
            stories.comicList.clear()
            stories.comicList.addAll(getComicList(it.getJSONArray(StoriesEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, StoriesEntry.CHARACTER) {
            stories.characterList.clear()
            stories.characterList.addAll(getCharacterList(it.getJSONArray(StoriesEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, StoriesEntry.EVENT) {
            stories.eventList.clear()
            stories.eventList.addAll(getEventList(it.getJSONArray(StoriesEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, StoriesEntry.CHARACTER) {
            stories.characterList.clear()
            stories.characterList.addAll(getCharacterList(it.getJSONArray(StoriesEntry.ITEM_KEYS)))
        }
        checkNullOrNotExistsJsonObject(jsonObject, StoriesEntry.CREATORS) {
            stories.creatorList.clear()
            stories.creatorList.addAll(getCreatorList(it.getJSONArray(StoriesEntry.ITEM_KEYS)))
        }
        return stories
    }

    private fun getThumbnailLink(path: String, extension: String): String {
        return "$path.$extension"
    }

    private fun checkNullOrNotExistsJsonObject(
        jsonObject: JSONObject,
        keyEntity: String = "",
        callback: (JSONObject) -> Unit,
    ) {
        if (keyEntity.isEmpty() && jsonObject != null){
            callback(jsonObject)
            return
        }

        if (jsonObject.has(keyEntity) && !jsonObject.isNull(keyEntity)) {
            callback(jsonObject.getJSONObject(keyEntity))
        }
    }
}
