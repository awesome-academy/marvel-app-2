package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Series(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val startYear: Int = 0,
    val endYear: Int = 0,
    val thumbnailLink: String = "",
    val detailLink: String = "",
    val listDetailContent: MutableList<DetailListItem> = mutableListOf(),
    var isFavorite: Boolean = false
) : Parcelable

object SeriesEntry {
    const val GET_TYPE = "type"
    const val GET_DETAIL_URL = "url"
    const val CHARACTER = "characters"
    const val START_YEAR = "startYear"
    const val END_YEAR = "endYear"
    const val TYPE_DETAIL_URL = "detail"
    const val DESCRIPTION = "description"
    const val TITLE = "title"
    const val EVENT = "event"
    const val ID = "id"
    const val THUMBNAIL_DIR = "thumbnail"
    const val GET_PATH = "path"
    const val EXTENSION = "extension"
    const val COMIC = "comics"
    const val ITEM_KEYS = "items"
    const val SERIES_ENTITY = "Series"
    const val STORIES = "stories"
    const val GET_MANY_URL = "urls"
    const val CREATORS = "creators"
}
