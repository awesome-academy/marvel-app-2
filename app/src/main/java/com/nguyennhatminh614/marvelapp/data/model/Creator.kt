package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Creator(
    val id: Int,
    val name: String,
    val thumbnailLink: String,
    val description: String,
    val comicList: List<ComicDTO>,
    val seriesList: List<SeriesDTO>,
    val storiesList: List<StoriesDTO>,
    val eventList: List<EventDTO>,
    val detailLink: String,
) : Parcelable
