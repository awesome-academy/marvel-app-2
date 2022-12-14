package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comic(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnailLink: String,
    val printPrice: Double = 0.0,
    val numberOfPages: Int = 0,
    val seriesDetail: DtoItem = DtoItem(),
    val listDetailContent: MutableList<DetailListItem> = mutableListOf(),
    val comicDetailLink: String = "",
    var isFavorite: Boolean = false,
) : Parcelable

object ComicEntry {
    const val COMIC_ENTITY = "Comics"
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
