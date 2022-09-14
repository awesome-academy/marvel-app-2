package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var thumbnailLink: String = "",
    val creatorList: MutableList<CreatorDTO> = mutableListOf(),
    val characterList: MutableList<CharacterDTO> = mutableListOf(),
    val seriesList: MutableList<SeriesDTO> = mutableListOf(),
    val comicList: MutableList<ComicDTO> = mutableListOf(),
    val storiesList: MutableList<StoriesDTO> = mutableListOf(),
    var startDate: String = "",
    var endDate: String = "",
    var detailLink : String = "",
    var wikiLink: String = "",
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
