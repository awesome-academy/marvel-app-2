package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchObject(
    var id: Int = 0,
    var category: String = "",
    var thumbnailLink: String = "",
    var title: String = "",
) : Parcelable
