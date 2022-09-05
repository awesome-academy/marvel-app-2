package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Creator(
    var id: Int = 0,
    var name: String = "",
    var thumbnailLink: String = "",
    var comicList: List<ComicDTO>? = null,
    var seriesList: List<SeriesDTO>? = null,
    var storiesList: List<StoriesDTO>? = null,
    var eventList: List<EventDTO>? = null,
    var detailLink: String = "",
) : Parcelable

object CreatorEntry {
    const val CREATOR_ENTITY = "creator"
    const val ID = "id"
    const val FULL_NAME = "fullName"
    const val THUMBNAIL_DIR = "thumbnail"
    const val GET_PATH = "path"
    const val EXTENSION = "extension"
    const val COMIC = "comics"
    const val ITEM_KEYS = "items"
    const val SERIES = "series"
    const val STORIES = "stories"
    const val EVENT = "events"
    const val GET_MANY_URL = "urls"
    const val GET_DETAIL_URL = "url"
}
