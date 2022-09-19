package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val thumbnailLink: String = "",
    val listDetailContent: MutableList<DetailListItem> = mutableListOf(),
    val detailUrl: String = "",
    val wikiUrl: String = "",
    val comicLinkUrl: String = "",
    var isFavorite: Boolean = false,
) : Parcelable

object CharacterEntry {
    const val EXTENSION = "extension"
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
    const val CHARACTER_ENTITY = "Characters"
    const val GET_DATA = "data"
    const val GET_RESPONSE_RESULT = "results"
}
