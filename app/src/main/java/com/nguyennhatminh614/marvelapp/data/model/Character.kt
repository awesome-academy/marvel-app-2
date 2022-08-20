package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var thumbnailLink: String = "",
    var comicList: List<ComicDTO>? = null,
    var seriesList: List<SeriesDTO>? = null,
    var storiesList: List<StoriesDTO>? = null,
    var eventList: List<EventDTO>? = null,
    var detailUrl: String = "",
    var wikiUrl: String = "",
    var comicLinkUrl: String = "",
    var isFavorite: Boolean = false,
) : Parcelable

object CharacterEntry{
    const val ID = "id"
    const val NAME = "name"
    const val DESCRIPTION = "description"
    const val THUMBNAIL_DIR = "thumbnail"
    const val GET_PATH = "path"
    const val COMIC = "comics"
    const val ITEM_KEYS = "items"
    const val SERIES = "series"
    const val STORIES = "stories"
    const val EVENT = "events"
    const val GET_MANY_URL = "urls"
    const val GET_DETAIL_URL = "url"
    const val GET_TYPE = "type"
    const val TYPE_DETAIL_URL = "detail"
    const val TYPE_WIKI_URL = "wiki"
    const val TYPE_COMIC_LINK_URL = "comiclink"
    const val TITLE = "title"
    const val ROLE = "role"
    const val CREATORS = "creators"
    const val CHARACTERS = "characters"
    const val PRICES = "prices"
    const val PRINT_PRICES = "printPrice"
    const val DETAIL_PRICE = "price"
    const val NUMBER_OF_PAGE = "pageCount"
    const val GET_DATA = "data"
    const val GET_RESPONSE_RESULT = "results"
}
