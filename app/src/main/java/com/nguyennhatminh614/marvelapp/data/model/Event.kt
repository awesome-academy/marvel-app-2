package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnailLink: String,
    val creatorList: List<CreatorDTO>,
    val characterList: List<CharacterDTO>,
    val seriesList: List<SeriesDTO>,
    val comicList: List<ComicDTO>,
    val eventList: List<EventDTO>,
    val isFavorite: Boolean = false,
) : Parcelable
