package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stories(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var thumbnailLink: String = "",
    val creatorList: MutableList<CreatorDTO> = mutableListOf(),
    val characterList: MutableList<CharacterDTO> = mutableListOf(),
    val seriesList: MutableList<SeriesDTO> = mutableListOf(),
    val comicList: MutableList<ComicDTO> = mutableListOf(),
    val eventList: MutableList<EventDTO> = mutableListOf(),
    var isFavorite: Boolean = false
) : Parcelable

object StoriesEntry {
    const val EVENT = "events"
    const val CHARACTER = "characters"
    const val DESCRIPTION = "description"
    const val TITLE = "title"
    const val ID = "id"
    const val THUMBNAIL_DIR = "thumbnail"
    const val GET_PATH = "path"
    const val EXTENSION = "extension"
    const val COMIC = "comics"
    const val ITEM_KEYS = "items"
    const val SERIES = "series"
    const val STORIES_ENTITY = "Stories"
    const val CREATORS = "creators"
}
