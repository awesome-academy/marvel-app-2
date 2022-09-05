package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comic(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var thumbnailLink: String = "",
    var printPrice: Double = 0.0,
    var numberOfPages: Int = 0,
    var seriesDetail: SeriesDTO? = null,
    var creatorList: List<CreatorDTO>? = null,
    var characterList: List<CharacterDTO>? = null,
    var storiesList: List<StoriesDTO>? = null,
    var eventList: List<EventDTO>? = null,
    var comicDetailLink: String? = null,
    var isFavorite: Boolean = false,
) : Parcelable

object ComicEntry {
    const val COMIC_ENTITY = "comic"
    const val EXTENSION = "extension"
    const val ID = "id"
    const val DESCRIPTION = "description"
    const val THUMBNAIL_DIR = "thumbnail"
    const val GET_PATH = "path"
    const val ITEM_KEYS = "items"
    const val SERIES = "series"
    const val STORIES = "stories"
    const val EVENT = "events"
    const val GET_MANY_URL = "urls"
    const val GET_DETAIL_URL = "url"
    const val GET_TYPE = "type"
    const val TYPE_DETAIL_URL = "detail"
    const val TITLE = "title"
    const val CREATORS = "creators"
    const val CHARACTERS = "characters"
    const val PRICES = "prices"
    const val PRINT_PRICES = "printPrice"
    const val DETAIL_PRICE = "price"
    const val NUMBER_OF_PAGE = "pageCount"
}
