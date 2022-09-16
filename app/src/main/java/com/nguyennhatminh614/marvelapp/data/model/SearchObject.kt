package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchObject(
    val id: Int = 0,
    val category: String = "",
    val thumbnailLink: String = "",
    val title: String = "",
) : Parcelable
