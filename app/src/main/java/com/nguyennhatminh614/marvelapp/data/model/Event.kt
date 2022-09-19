package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val thumbnailLink: String = "",
    val listDetailContent: MutableList<DetailListItem> = mutableListOf(),
    val startDate: String = "",
    val endDate: String = "",
    val detailLink : String = "",
    val wikiLink: String = "",
    var isFavorite: Boolean = false,
) : Parcelable

object EventEntry {
    const val CHARACTER = "characters"
    const val START_DATE = "start"
    const val END_DATE = "end"
    const val TYPE_WIKI_URL = "wiki"
    const val TYPE_DETAIL_URL = "detail"
    const val DESCRIPTION = "description"
    const val TITLE = "title"
    const val EVENT_ENTITY = "Events"
    const val ID = "id"
    const val THUMBNAIL_DIR = "thumbnail"
    const val GET_PATH = "path"
    const val EXTENSION = "extension"
    const val COMIC = "comics"
    const val ITEM_KEYS = "items"
    const val SERIES = "series"
    const val STORIES = "stories"
    const val GET_MANY_URL = "urls"
    const val CREATORS = "creators"
}
