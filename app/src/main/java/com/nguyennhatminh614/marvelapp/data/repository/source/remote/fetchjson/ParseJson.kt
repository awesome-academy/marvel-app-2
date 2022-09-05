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
        character.id = jsonObject.getInt(CharacterEntry.ID)
        character.name = jsonObject.optString(CharacterEntry.NAME)
        character.description = jsonObject.optString(CharacterEntry.DESCRIPTION)
        if (character.description.isEmpty()) {
            character.description = NO_DESCRIPTION
        }
        jsonObject.getJSONObject(CharacterEntry.THUMBNAIL_DIR).apply {
            character.thumbnailLink = getThumbnailLink(
                optString(CharacterEntry.GET_PATH),
                optString(CharacterEntry.EXTENSION)
            )
        }

        character.comicList = getComicList(
            jsonObject.getJSONObject(CharacterEntry.COMIC).getJSONArray(CharacterEntry.ITEM_KEYS)
        )
        character.eventList = getEventList(
            jsonObject.getJSONObject(CharacterEntry.EVENT).getJSONArray(CharacterEntry.ITEM_KEYS)
        )
        character.seriesList = getSeriesList(
            jsonObject.getJSONObject(CharacterEntry.SERIES).getJSONArray(CharacterEntry.ITEM_KEYS)
        )
        character.storiesList = getStoriesList(
            jsonObject.getJSONObject(CharacterEntry.STORIES).getJSONArray(CharacterEntry.ITEM_KEYS)
        )

        jsonObject.getJSONArray(CharacterEntry.GET_MANY_URL).apply {
            for (i in 0 until length()) {
                val tempJsonObject = getJSONObject(i)
                val urlString = tempJsonObject.optString(CharacterEntry.GET_DETAIL_URL)

                when (tempJsonObject.optString(CharacterEntry.GET_TYPE)) {
                    CharacterEntry.TYPE_DETAIL_URL -> character.detailUrl = urlString
                    CharacterEntry.TYPE_WIKI_URL -> character.wikiUrl = urlString
                    CharacterEntry.TYPE_COMIC_LINK_URL -> character.comicLinkUrl = urlString
                }
            }
        }

        return character
    }

    fun comicParseJson(jsonObject: JSONObject): Comic {
        val comic = Comic()
        comic.id = jsonObject.getInt(ComicEntry.ID)
        comic.title = jsonObject.optString(ComicEntry.TITLE)
        comic.description = jsonObject.optString(ComicEntry.DESCRIPTION)
        if (comic.description.isEmpty()) {
            comic.description = NO_DESCRIPTION
        }
        jsonObject.getJSONArray(ComicEntry.PRICES).getJSONObject(Constant.FIRST_POSITION)
            .also { thisElem ->
                if (thisElem.optString(ComicEntry.GET_TYPE)
                        .equals(ComicEntry.PRINT_PRICES)
                ) {
                    comic.printPrice = thisElem.getDouble(ComicEntry.DETAIL_PRICE)
                }
            }
        comic.numberOfPages = jsonObject.getInt(ComicEntry.NUMBER_OF_PAGE)

        jsonObject.getJSONObject(ComicEntry.THUMBNAIL_DIR).apply {
            comic.thumbnailLink = getThumbnailLink(
                optString(ComicEntry.GET_PATH),
                optString(ComicEntry.EXTENSION)
            )
        }

        comic.creatorList = getCreatorList(
            jsonObject.getJSONObject(ComicEntry.CREATORS).getJSONArray(ComicEntry.ITEM_KEYS)
        )
        comic.characterList = getCharacterList(
            jsonObject.getJSONObject(ComicEntry.CHARACTERS)
                .getJSONArray(ComicEntry.ITEM_KEYS)
        )
        comic.storiesList = getStoriesList(
            jsonObject.getJSONObject(ComicEntry.STORIES).getJSONArray(ComicEntry.ITEM_KEYS)
        )

        comic.seriesDetail = SeriesDTO().apply {
            jsonObject.getJSONObject(ComicEntry.SERIES).let {
                resourceUrl = it.optString(APIConstant.GET_RESOURCE_URI)
                textDescription = it.optString(APIConstant.NAME)
            }
        }

        comic.eventList = getEventList(
            jsonObject.getJSONObject(ComicEntry.EVENT).getJSONArray(ComicEntry.ITEM_KEYS)
        )

        jsonObject.getJSONArray(ComicEntry.GET_MANY_URL).getJSONObject(Constant.FIRST_POSITION)
            .also { thisElem ->
                run {
                    if (thisElem.optString(ComicEntry.GET_TYPE)
                            .equals(ComicEntry.TYPE_DETAIL_URL)
                    ) {
                        comic.comicDetailLink = thisElem.optString(ComicEntry.GET_DETAIL_URL)
                    }
                }
            }

        return comic
    }

    fun creatorParseJson(jsonObject: JSONObject): Creator {
        val creator = Creator()

        creator.id =
            jsonObject.getInt(CreatorEntry.ID)
        creator.name = jsonObject.optString(CreatorEntry.FULL_NAME)

        jsonObject.getJSONObject(CreatorEntry.THUMBNAIL_DIR).apply {
            creator.thumbnailLink = getThumbnailLink(
                optString(CreatorEntry.GET_PATH),
                optString(CreatorEntry.EXTENSION)
            )
        }

        creator.comicList = getComicList(
            jsonObject.getJSONObject(CreatorEntry.COMIC).getJSONArray(CreatorEntry.ITEM_KEYS)
        )

        creator.eventList = getEventList(
            jsonObject.getJSONObject(CreatorEntry.EVENT).getJSONArray(CreatorEntry.ITEM_KEYS)
        )

        creator.seriesList = getSeriesList(
            jsonObject.getJSONObject(CreatorEntry.SERIES).getJSONArray(CreatorEntry.ITEM_KEYS)
        )

        creator.storiesList = getStoriesList(
            jsonObject.getJSONObject(CreatorEntry.STORIES)
                .getJSONArray(CreatorEntry.ITEM_KEYS)
        )

        creator.detailLink = jsonObject.getJSONArray(CreatorEntry.GET_MANY_URL)
            .getJSONObject(Constant.FIRST_POSITION)
            .optString(CreatorEntry.GET_DETAIL_URL)

        return creator
    }

    fun eventParseJson(jsonObject: JSONObject): Event {
        val event = Event()
        event.id = jsonObject.getInt(EventEntry.ID)
        event.title = jsonObject.optString(EventEntry.TITLE)
        event.description = jsonObject.optString(EventEntry.DESCRIPTION)
        if (event.description.isEmpty()){
            event.description = NO_DESCRIPTION
        }
        jsonObject.getJSONArray(EventEntry.GET_MANY_URL).apply {
            for (i in 0 until length()) {
                val tempJsonObject = getJSONObject(i)
                val urlString = tempJsonObject.optString(CharacterEntry.GET_DETAIL_URL)

                when (tempJsonObject.optString(CharacterEntry.GET_TYPE)) {
                    EventEntry.TYPE_DETAIL_URL -> event.detailLink = urlString
                    EventEntry.TYPE_WIKI_URL -> event.wikiLink = urlString
                }
            }
        }

        event.startDate = jsonObject.optString(EventEntry.START_DATE).parseDateTime()
        event.endDate = jsonObject.optString(EventEntry.END_DATE).parseDateTime()

        jsonObject.getJSONObject(EventEntry.THUMBNAIL_DIR).apply {
            event.thumbnailLink = getThumbnailLink(
                optString(EventEntry.GET_PATH),
                optString(EventEntry.EXTENSION),
            )
        }

        event.creatorList = getCreatorList(
            jsonObject.getJSONObject(EventEntry.COMIC).getJSONArray(EventEntry.ITEM_KEYS)
        )

        event.characterList = getCharacterList(
            jsonObject.getJSONObject(EventEntry.CHARACTER).getJSONArray(EventEntry.ITEM_KEYS)
        )

        event.storiesList = getStoriesList(
            jsonObject.getJSONObject(EventEntry.STORIES).getJSONArray(EventEntry.ITEM_KEYS)
        )

        event.comicList = getComicList(
            jsonObject.getJSONObject(EventEntry.COMIC).getJSONArray(EventEntry.ITEM_KEYS)
        )

        event.seriesList = getSeriesList(
            jsonObject.getJSONObject(EventEntry.SERIES).getJSONArray(EventEntry.ITEM_KEYS)
        )

        return event
    }

    fun seriesParseJson(jsonObject: JSONObject): Series {
        val series = Series()
        series.id = jsonObject.getInt(SeriesEntry.ID)
        series.title = jsonObject.optString(SeriesEntry.TITLE)
        series.description = jsonObject.optString(SeriesEntry.DESCRIPTION)
        if (series.description.isEmpty()) {
            series.description = NO_DESCRIPTION
        }
        series.startYear = jsonObject.getInt(SeriesEntry.START_YEAR)
        series.endYear = jsonObject.getInt(SeriesEntry.END_YEAR)
        jsonObject.getJSONArray(SeriesEntry.GET_MANY_URL).getJSONObject(Constant.FIRST_POSITION)
            .apply {
                if (this != null){
                    val urlString = jsonObject.optString(SeriesEntry.GET_DETAIL_URL) ?: ""
                    when (jsonObject.optString(SeriesEntry.GET_TYPE)) {
                        SeriesEntry.TYPE_DETAIL_URL -> series.detailLink = urlString
                    }
                }
            }
        series.rating = jsonObject.optString(SeriesEntry.RATING)
        jsonObject.getJSONObject(SeriesEntry.THUMBNAIL_DIR).apply {
            series.thumbnailLink = getThumbnailLink(
                optString(SeriesEntry.GET_PATH),
                optString(SeriesEntry.EXTENSION),
            )
        }
        series.creatorList = getCreatorList(
            jsonObject.getJSONObject(SeriesEntry.COMIC).getJSONArray(SeriesEntry.ITEM_KEYS)
        )

        series.characterList = getCharacterList(
            jsonObject.getJSONObject(SeriesEntry.CHARACTER).getJSONArray(SeriesEntry.ITEM_KEYS)
        )

        series.storiesList = getStoriesList(
            jsonObject.getJSONObject(SeriesEntry.STORIES).getJSONArray(SeriesEntry.ITEM_KEYS)
        )

        series.comicList = getComicList(
            jsonObject.getJSONObject(SeriesEntry.COMIC).getJSONArray(SeriesEntry.ITEM_KEYS)
        )

        series.eventList = getEventList(
            jsonObject.getJSONObject(SeriesEntry.EVENT).getJSONArray(SeriesEntry.ITEM_KEYS)
        )
        return series
    }

    fun storiesParseJson(jsonObject: JSONObject): Stories {
        val stories = Stories()
        stories.id = jsonObject.getInt(StoriesEntry.ID)
        stories.title = jsonObject.optString(StoriesEntry.TITLE)
        stories.description = jsonObject.optString(StoriesEntry.DESCRIPTION)
        if(stories.description.isEmpty()) {
            stories.description = NO_DESCRIPTION
        }
        jsonObject.getJSONObject(StoriesEntry.THUMBNAIL_DIR).apply {
            stories.thumbnailLink = getThumbnailLink(
                optString(StoriesEntry.GET_PATH),
                optString(StoriesEntry.EXTENSION),
            )
        }
        stories.creatorList = getCreatorList(
            jsonObject.getJSONObject(StoriesEntry.COMIC).getJSONArray(StoriesEntry.ITEM_KEYS)
        )

        stories.characterList = getCharacterList(
            jsonObject.getJSONObject(StoriesEntry.CHARACTER).getJSONArray(StoriesEntry.ITEM_KEYS)
        )

        stories.seriesList = getSeriesList(
            jsonObject.getJSONObject(StoriesEntry.SERIES).getJSONArray(StoriesEntry.ITEM_KEYS)
        )

        stories.comicList = getComicList(
            jsonObject.getJSONObject(StoriesEntry.COMIC).getJSONArray(StoriesEntry.ITEM_KEYS)
        )

        stories.eventList = getEventList(
            jsonObject.getJSONObject(StoriesEntry.EVENT).getJSONArray(StoriesEntry.ITEM_KEYS)
        )
        return stories
    }

    private fun getThumbnailLink(path: String, extension: String): String {
        return "$path.$extension"
    }
}
