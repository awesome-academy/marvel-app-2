package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Series(
    val id: Int,
    val title: String,
    val description: String,
    val startYear: Int,
    val endYear: Int,
    val rating: String,
    val thumbnailLink: String,
    val creatorList: List<CreatorDTO>,
    val characterList: List<CharacterDTO>,
    val eventList: List<EventDTO>,
    val storiesList: List<StoriesDTO>,
    val comicList: List<ComicDTO>,
    val isFavorite: Boolean = false
) : Parcelable
