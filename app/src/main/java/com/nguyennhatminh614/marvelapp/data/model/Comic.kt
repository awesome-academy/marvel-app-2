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
    var seriesList: List<SeriesDTO>? = null,
    var creatorList: List<CreatorDTO>? = null,
    var characterList: List<CharacterDTO>? = null,
    var storiesList: List<StoriesDTO>? = null,
    var eventList: List<EventDTO>? = null,
    var comicDetailLink: String? = null,
    var isFavorite: Boolean = false,
) : Parcelable
